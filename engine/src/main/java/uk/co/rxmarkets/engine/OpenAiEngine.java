package uk.co.rxmarkets.engine;

import com.theokanning.openai.completion.chat.ChatCompletionChoice;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatCompletionResult;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.service.OpenAiService;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import uk.co.rxmarkets.engine.model.Opinion;

import java.time.Duration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.enterprise.context.ApplicationScoped;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;

@Slf4j
@Getter
@ApplicationScoped
public class OpenAiEngine implements Engine {

    private final String model;
    private final String token;
    private final OpenAiService service;

    public OpenAiEngine() {
        final Properties properties = loadProperties();
        this.model = properties.getProperty("model");
        this.token = properties.getProperty("token");
        if (token == null || token.isBlank()) throw new IllegalArgumentException("No OpenAI token specified.");
        this.service = new OpenAiService(token, Duration.ofSeconds(30));
        log.info("Configured OpenAI with \"{}\" model", model);
    }

    @SneakyThrows
    private static Properties loadProperties() {
        final Properties prop = new Properties();
        try (InputStream is = OpenAiEngine.class.getClassLoader().getResourceAsStream("openai.properties")) {
            prop.load(is);
            return prop;
        }
    }

    @Override
    public double score(String prompt, Set<Opinion> data) {
        List<Double> scores = data.stream()
                .map(message -> generateScore(prompt, message.getData()))
                .filter(score -> score != -1.0).toList();
        return scores.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
    }

    private Double generateScore(String prompt, String message) {
        List<ChatMessage> messages = new ArrayList<>();
        messages.add(new ChatMessage("system", prompt));
        messages.add(new ChatMessage("user", message));
        ChatCompletionRequest completionRequest = ChatCompletionRequest.builder()
                .messages(messages)
                .model(model)
                .build();
        ChatCompletionResult completion = service.createChatCompletion(completionRequest);
        ChatCompletionChoice chatCompletionChoice = completion.getChoices().stream().findFirst().orElseThrow();
        String reply = chatCompletionChoice.getMessage().getContent();
        return extractDouble(reply);
    }

    private static Double extractDouble(String input) {
        Pattern pattern = Pattern.compile("[-+]?[0-9]*\\.?[0-9]+([eE][-+]?[0-9]+)?");
        Matcher matcher = pattern.matcher(input);
        if (matcher.find()) {
            return Double.parseDouble(matcher.group());
        } else {
            log.warn("Could not parse response: {}", input);
            return -1.0;
        }
    }

}

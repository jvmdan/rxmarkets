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
    private final String prompt;
    private final OpenAiService service;

    public OpenAiEngine() {
        final Properties properties = loadProperties();
        this.model = properties.getProperty("model");
        this.token = properties.getProperty("token");
        this.prompt = properties.getProperty("prompt");
        if (token == null || token.isBlank()) throw new IllegalArgumentException("No OpenAI token specified.");
        this.service = new OpenAiService(token);
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
    public double score(String category, Set<Opinion> data) {
        log.info("Scoring {} across {} data points...", category, data.size());
        List<Double> scores = data.stream()
                .map(message -> generateScore(category, message.getData()))
                .filter(score -> score != -1).toList();
        double average = scores.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
        log.info("Average score for {}: {}", category, average);
        return average;
    }

    private Double generateScore(String category, String message) {
        List<ChatMessage> messages = new ArrayList<>();
        messages.add(new ChatMessage("system", prompt + " " + category));
        messages.add(new ChatMessage("user", message));
        ChatCompletionRequest completionRequest = ChatCompletionRequest.builder()
                .messages(messages)
                .model(model)
                .build();
        ChatCompletionResult completion = service.createChatCompletion(completionRequest);
        ChatCompletionChoice chatCompletionChoice = completion.getChoices().stream().findFirst().orElseThrow();
        String reply = chatCompletionChoice.getMessage().getContent();
        double result = -1;
        try {
            result = Double.parseDouble(reply);
        } catch (Exception e) {
            log.warn("Could not parse response: {} the original request data was: {}", reply, message);
        }
        return result;
    }

}

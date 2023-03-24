package uk.co.rxmarkets.engine;

import com.theokanning.openai.completion.chat.ChatCompletionChoice;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatCompletionResult;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.service.OpenAiService;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import uk.co.rxmarkets.model.Engine;
import uk.co.rxmarkets.model.ranking.Ranked;
import uk.co.rxmarkets.model.scoring.Category;
import uk.co.rxmarkets.model.scoring.Indicator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;

@Slf4j
@Getter
public class OpenAiEngine implements Engine<Category, Ranked> {

    private final String model;
    private final String token;
    private final String prompt;
    private final OpenAiService service;

    public OpenAiEngine() {
        final Properties properties = loadProperties();
        this.model = properties.getProperty("model");
        this.token = properties.getProperty("token");
        this.prompt = properties.getProperty("prompt");
        this.service = new OpenAiService(token);
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
    public Indicator score(Category category, Set<Ranked> ranked) {
        List<Double> scores = ranked.stream()
                .map(message -> generateScore(category, message.getData()))
                .filter(score -> score != -1).toList();
        return new Indicator(scores.stream().mapToDouble(Double::doubleValue).average().orElse(0.0), 1);
    }

    private Double generateScore(Category category, String message) {
        List<ChatMessage> messages = new ArrayList<>();
        messages.add(new ChatMessage("system", prompt + " " + category.getDescription()));
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

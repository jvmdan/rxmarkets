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
import uk.co.rxmarkets.model.ranking.Opinion;
import uk.co.rxmarkets.model.scoring.Category;
import uk.co.rxmarkets.model.scoring.Indicator;

import javax.enterprise.context.ApplicationScoped;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;

@Slf4j
@Getter
@ApplicationScoped
public class OpenAiEngine implements Engine<Category, Opinion> {

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
    public Indicator score(Category category, Set<Opinion> ranked) {
        log.info("Scoring {} across {} data points...", category.name(), ranked.size());
        List<Double> scores = ranked.stream()
                .map(message -> generateScore(category, message.getData()))
                .filter(score -> score != -1).toList();
        double average = scores.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
        log.info("Average score for {}: {}", category.name(), average);
        return new Indicator(average, 1);
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

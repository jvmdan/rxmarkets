package uk.co.rxmarkets.engine;

import com.theokanning.openai.completion.chat.ChatCompletionChoice;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatCompletionResult;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.service.OpenAiService;
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
import java.util.Set;

@Slf4j
public class OpenAiEngine implements Engine<Category, Ranked> {

    private static final String OPENAI_MODEL = "gpt-3.5-turbo";

    private final String prompt;
    private final OpenAiService service;

    public OpenAiEngine() {
        this.prompt = loadStringFromResourceFile("prompt.txt");
        String test = loadStringFromResourceFile("token.txt");
        this.service = new OpenAiService(test);
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
                .model(OPENAI_MODEL)
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

    private static String loadStringFromResourceFile(String fileName) {
        final StringBuilder contentBuilder = new StringBuilder();
        try (InputStream is = OpenAiEngine.class.getClassLoader().getResourceAsStream(fileName)) {
            assert is != null;
            try (InputStreamReader input = new InputStreamReader(is, StandardCharsets.UTF_8);
                 BufferedReader buf = new BufferedReader(input)) {
                String line;
                while ((line = buf.readLine()) != null) {
                    contentBuilder.append(line).append(System.lineSeparator());
                }
            }
        } catch (IOException e) {
            log.error("Error while reading the file: {}", fileName);
            e.printStackTrace();
        }
        return contentBuilder.toString().replace("\n", "").replace("\r", "");
    }

}

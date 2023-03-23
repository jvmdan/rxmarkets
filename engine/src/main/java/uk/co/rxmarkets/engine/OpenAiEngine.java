package uk.co.rxmarkets.engine;

import com.theokanning.openai.completion.chat.ChatCompletionChoice;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatCompletionResult;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.service.OpenAiService;
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

public class OpenAiEngine implements Engine<Category, Ranked> {

    final OpenAiService service;
    final String prompt;

    public OpenAiEngine(){
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

    private Double generateScore(Category category, String message){
        List<ChatMessage> messages = new ArrayList<>();
        messages.add(new ChatMessage("system", prompt + " " + category.getDescription()));
        messages.add(new ChatMessage("user", message));
        ChatCompletionRequest completionRequest = ChatCompletionRequest.builder()
                .messages(messages)
                .model("gpt-3.5-turbo")
                .build();
        ChatCompletionResult completion = service.createChatCompletion(completionRequest);
        ChatCompletionChoice chatCompletionChoice = completion.getChoices().stream().findFirst().orElseThrow();
        String reply = chatCompletionChoice.getMessage().getContent();
        double result = -1;
        try {
            result = Double.parseDouble(reply);
        } catch (Exception e){
            System.out.println("Could not parse response: " + reply + " the original request data was: "+ message);
        }
        return result;
    }

    private static String loadStringFromResourceFile(String fileName) {
        StringBuilder contentBuilder = new StringBuilder();

        try (InputStream inputStream = OpenAiEngine.class.getClassLoader().getResourceAsStream(fileName)) {
            assert inputStream != null;
            try (InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
                 BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {

                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    contentBuilder.append(line).append(System.lineSeparator());
                }
            }
        } catch (IOException e) {
            System.err.println("Error while reading the file: " + fileName);
            e.printStackTrace();
        }

        return contentBuilder.toString().replace("\n", "").replace("\r", "");
    }
}

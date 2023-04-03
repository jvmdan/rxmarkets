package uk.co.rxmarkets.gatherer.reddit;


import io.smallrye.mutiny.Uni;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.WebClientOptions;
import io.vertx.mutiny.core.Vertx;
import io.vertx.mutiny.core.buffer.Buffer;
import io.vertx.mutiny.ext.web.client.HttpResponse;
import io.vertx.mutiny.ext.web.client.WebClient;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import uk.co.rxmarkets.gatherer.Scraper;
import uk.co.rxmarkets.model.ranking.Opinion;

import javax.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
@Getter
@Slf4j
public class RedditScraper implements Scraper<WebClient> {

    private static final String BASE_URL = "https://www.reddit.com";

    private final WebClient client;

    public RedditScraper(Vertx vertx) {
        this.client = WebClient.create(vertx);
    }

    @SneakyThrows
    public Uni<JsonArray> scrape(String subreddit, int count) {
        final String endpoint = String.format("%s/r/%s/new.json?limit=", BASE_URL, subreddit) + count;
        return client.getAbs(endpoint)
                .putHeader("User-Agent", "RedditApiClient/1.0") // Required by Reddit API
                .send()
                .map(resp -> {
                    if (resp.statusCode() == 200) {
                        final List<Opinion> data = extractData(resp);
                        return new JsonArray(data);
                    } else {
                        return null;
                    }});
    }

    private static List<Opinion> extractData(HttpResponse<Buffer> resp) {
        // Extract the posts from the response body received from the Reddit API.
        final List<Opinion> data = new ArrayList<>();
        final JsonObject body = resp.bodyAsJsonObject();
        final List<JsonObject> posts = body.getJsonObject("data")
                .getJsonArray("children")
                .stream()
                .map(entry -> ((JsonObject) entry).getJsonObject("data"))
                .toList();

        // For each post, extract the permalink & the data within.
        posts.forEach(post -> {
            final String permalink = post.getString("permalink");
            final String content = post.getString("selftext");
            data.add(new Opinion(permalink, content));
        });
        return data;
    }

}

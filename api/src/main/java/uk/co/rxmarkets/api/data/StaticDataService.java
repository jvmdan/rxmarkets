package uk.co.rxmarkets.api.data;

import io.quarkus.vertx.ConsumeEvent;
import io.smallrye.mutiny.Uni;
import io.vertx.core.json.JsonArray;
import io.vertx.mutiny.core.Vertx;
import lombok.RequiredArgsConstructor;

import javax.enterprise.context.ApplicationScoped;
import java.nio.charset.StandardCharsets;

/**
 * The StaticDataService is a singleton service available within the application context,
 * that listens for events on the bus and processes static data accordingly. In this case,
 * we deal only with local sources of information, returning them back to the caller
 * as a JsonArray containing multiple JsonObjects.
 */
@ApplicationScoped
@RequiredArgsConstructor
public class StaticDataService {

    private final Vertx vertx;

    @ConsumeEvent("tweets")
    public Uni<JsonArray> readTweets(String file) {
        return vertx.fileSystem().readFile("static/" + file)
                .onItem().transform(content -> {
                    final String s = content.toString(StandardCharsets.UTF_8);
                    return new JsonArray(s);
                });
    }

}


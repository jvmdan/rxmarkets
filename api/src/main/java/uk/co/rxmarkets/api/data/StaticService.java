package uk.co.rxmarkets.api.data;

import io.quarkus.vertx.ConsumeEvent;
import io.smallrye.mutiny.Uni;
import io.vertx.core.json.JsonArray;
import io.vertx.mutiny.core.Vertx;
import lombok.RequiredArgsConstructor;

import javax.enterprise.context.ApplicationScoped;
import java.nio.charset.StandardCharsets;

@ApplicationScoped
@RequiredArgsConstructor
public class StaticService {

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


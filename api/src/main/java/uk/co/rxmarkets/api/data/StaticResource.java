package uk.co.rxmarkets.api.data;

import io.smallrye.mutiny.Uni;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.mutiny.core.Vertx;
import lombok.RequiredArgsConstructor;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.nio.charset.StandardCharsets;

@RequiredArgsConstructor
@Path("api/static")
public class StaticResource {

    private final Vertx vertx;

    @GET
    @Path("/tweets")
    public Uni<JsonArray> readStaticTweets() {
        return vertx.fileSystem().readFile("static/cs-tweets.json")
                .onItem().transform(content -> {
                    final String s = content.toString(StandardCharsets.UTF_8);
                    return new JsonArray(s);
                });
    }

}

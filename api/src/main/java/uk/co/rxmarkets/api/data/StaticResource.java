package uk.co.rxmarkets.api.data;

import io.smallrye.mutiny.Uni;
import io.vertx.core.json.JsonArray;
import io.vertx.mutiny.core.Vertx;
import io.vertx.mutiny.core.eventbus.EventBus;
import io.vertx.mutiny.core.eventbus.Message;
import lombok.RequiredArgsConstructor;
import org.jboss.resteasy.reactive.RestQuery;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.nio.charset.StandardCharsets;

@RequiredArgsConstructor
@Path("api/static")
public class StaticResource {

    private final EventBus bus;

    @GET
    @Path("/tweets")
    public Uni<JsonArray> readTweets(@RestQuery String prefix) {
        final String suffix = "-tweets.json";
        return bus.<JsonArray>request("tweets", prefix + suffix)
                .onItem().transform(Message::body);
    }

}

package uk.co.rxmarkets.api.rest;

import io.smallrye.mutiny.Uni;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.mutiny.core.eventbus.EventBus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.jboss.resteasy.reactive.RestQuery;
import uk.co.rxmarkets.model.EngineRequest;
import uk.co.rxmarkets.model.ranking.Opinion;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

@RequiredArgsConstructor
@Path("/scores")
@Slf4j
public class ScoresResource {

    private final EventBus bus;

    @Channel("engine-requests")
    Emitter<EngineRequest> emitter;

    /**
     * Endpoint to generate a new request ID and send it to "engine-requests" RabbitMQ exchange using the emitter.
     */
    @POST
    @Path("/request")
    @Produces(MediaType.TEXT_PLAIN)
    public Uni<String> createRequest(@RestQuery String equity) {
        return extractData(equity)
                .onItem().transform(data -> {
                    final EngineRequest request = new EngineRequest("XLON", equity.toUpperCase(Locale.ROOT), data);
                    emitter.send(request);
                    return request.getId().toString();
                });
    }

    // Pull the dataset out from the static file of tweets.
    public Uni<Set<Opinion>> extractData(String equity) {
        final String exampleFile = equity + "-tweets.json";
        return bus.<JsonArray>request("tweets", exampleFile)
                .onItem().transform(message -> {
                    final Set<Opinion> dataSet = new HashSet<>(message.body().size());
                    message.body().stream()
                            .filter(JsonObject.class::isInstance)
                            .map(JsonObject.class::cast)
                            .forEach(e -> {
                                final String source = e.getString("author");
                                final String data = e.getString("data");
                                dataSet.add(new Opinion(source, data));
                            });
                    return dataSet;
                });
    }

}

package uk.co.rxmarkets.api;

import io.smallrye.mutiny.Multi;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import uk.co.rxmarkets.model.EngineRequest;
import uk.co.rxmarkets.model.scoring.Scoreboard;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Collections;

@Path("/triggers")
public class RequestsResource {

    @Channel("engine-requests")
    Emitter<EngineRequest> emitter;

    @Channel("triggers")
    Multi<Scoreboard> triggers;

    /**
     * Endpoint retrieving the "triggers" queue and sending the items to a server sent event.
     */
    @GET
    @Produces(MediaType.SERVER_SENT_EVENTS)
    public Multi<Scoreboard> stream() {
        return triggers;
    }

    /**
     * Endpoint to generate a new quote request id and send it to "engine-requests" RabbitMQ exchange using the emitter.
     */
    @POST
    @Path("/request")
    @Produces(MediaType.TEXT_PLAIN)
    public String createRequest() {
        final EngineRequest request = new EngineRequest("XLON", "CS", Collections.emptySet());
        emitter.send(request);
        return request.getId().toString();
    }

}

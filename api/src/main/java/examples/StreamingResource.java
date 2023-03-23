package examples;

import io.smallrye.mutiny.Multi;
import io.vertx.mutiny.core.Vertx;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Date;

@Path("api/live")
public class StreamingResource {

    @Inject
    Vertx vertx;

    @GET
    @Produces(MediaType.SERVER_SENT_EVENTS)
    @Path("{market}/{ticker}")
    public Multi<String> greeting(String market, String ticker) {
        return vertx.periodicStream(2000).toMulti()
                .map(l -> String.format("Live Price for %s: £0.00 (%s)%n", ticker, new Date()));
    }

}

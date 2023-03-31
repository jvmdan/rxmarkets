package uk.co.rxmarkets.api.resources;

import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import lombok.RequiredArgsConstructor;
import org.jboss.resteasy.reactive.RestQuery;
import uk.co.rxmarkets.api.services.persistence.InMemoryService;
import uk.co.rxmarkets.model.assets.Equity;
import uk.co.rxmarkets.model.scoring.Scoreboard;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Path("api/scores")
@RequiredArgsConstructor
public class ScoreResource {

    private final PgPool client;
    private final InMemoryService store; // FIXME | This needs to retrieve from the database.

    @GET
    @Path("/{market}/{ticker}")
    public List<Scoreboard> getAll(String market, String ticker, @RestQuery String from, @RestQuery String to) {
        // TODO | Support filtering the scoreboards within the given date span.
        return store.getAll(ticker);
    }

    @GET
    @Path("/{market}/{ticker}/latest")
    public Uni<Response> getLatest(String market, String ticker) {
        return Equity.findByTicker(client, market, ticker)
                .onItem().transform(equity -> equity != null
                        ? Response.ok(equity.getScores().get(equity.getScores().size() - 1))
                        : Response.status(Status.NOT_FOUND))
                .onItem().transform(ResponseBuilder::build);
    }

    @GET
    @Path("/random")
    public List<Scoreboard> randomDataset() {
        // TODO | Used only in testing, this needs to be removed in the near future.
        final int nDays = 28; // The number of random days to generate.
        final long dayInMillis = 1000 * 60 * 60 * 24;
        final Date today = Date.from(Instant.now());
        final List<Scoreboard> scores = new ArrayList<>();
        for (int i = 0; i < nDays; i++) {
            final Date date = new Date(today.getTime() - (i * dayInMillis));
            final Scoreboard result = Scoreboard.random(date);
            scores.add(result);
        }
        return scores;
    }


}

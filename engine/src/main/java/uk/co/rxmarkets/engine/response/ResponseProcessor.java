package uk.co.rxmarkets.engine.response;

import io.smallrye.mutiny.Uni;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.hibernate.reactive.mutiny.Mutiny;
import uk.co.rxmarkets.engine.entities.Equity;
import uk.co.rxmarkets.engine.entities.Scoreboard;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
@RequiredArgsConstructor
@Slf4j
public class ResponseProcessor {

    private final Mutiny.SessionFactory sf;

    /**
     * The "onResponse" event is triggered when the 'response' data pipe contains a scoreboard
     * value given equity. This event results in the asynchronous persistence of the updated
     * equity to the underlying data store. The caller can choose to wait for the response,
     * or they can simply fire & forget.
     *
     * @return the Scoreboard instance that has been persisted to the database.
     */
    @Incoming("response")
    public Uni<Scoreboard> onResponse(EngineResponse response) {
        // Retrieve the equity instance from our database.
        log.info("Received response for request: {}", response.getRequestId());
        final Uni<Equity> equity = sf.withTransaction((s, t) -> s.find(Equity.class, response.getTicker()));

        // Now we can create a new scoreboard instance, tied to our equity.
        final Uni<Scoreboard> scoreboard = equity.onItem().ifNotNull().transform(e -> {
            final Scoreboard s = new Scoreboard();
            s.setEquity(e);
            s.setDate(response.getDate());
            s.setScores(response.getScores());
            return s;
        });

        // Persist the scoreboard to the database & return in a Uni.
        return scoreboard.onItem().transformToUni(sboard ->
                sf.withTransaction((s, t) -> s.persist(sboard)).replaceWith(sboard));
    }

}

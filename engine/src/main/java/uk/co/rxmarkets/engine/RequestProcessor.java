package uk.co.rxmarkets.engine;

import io.smallrye.reactive.messaging.annotations.Blocking;
import io.vertx.core.json.JsonObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Outgoing;
import uk.co.rxmarkets.api.model.Engine;
import uk.co.rxmarkets.api.model.ranking.Opinion;
import uk.co.rxmarkets.api.model.scoring.Category;
import uk.co.rxmarkets.api.model.scoring.Scoreboard;

import javax.enterprise.context.ApplicationScoped;

/**
 * A bean consuming data from the "request" RabbitMQ queue and giving out a random quote.
 * The result is pushed to the "scores" RabbitMQ exchange.
 */
@ApplicationScoped
@RequiredArgsConstructor
@Slf4j
public class RequestProcessor {

    private final Engine<Category, Opinion> engine;

    @Incoming("requests")
    @Outgoing("scores")
    @Blocking
    public Scoreboard process(JsonObject json) {
//        final EngineRequest request = json.mapTo(EngineRequest.class);
//        final UUID requestId = request.getId();
//        final Scoreboard.Builder scoreboard = new Scoreboard.Builder(requestId);
//        Arrays.stream(Category.values()).forEach(c -> {
//            final Indicator score = engine.score(c, request.getDataSet());
//            scoreboard.addScore(c, score);
//        });
//        log.info("Created Scoreboard instance for request: {}", request);
//        return scoreboard.build();
        return null;
    }

}

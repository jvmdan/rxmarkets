package uk.co.rxmarkets.engine.requests;

import io.smallrye.mutiny.Uni;
import io.smallrye.reactive.messaging.annotations.Blocking;
import io.smallrye.reactive.messaging.rabbitmq.IncomingRabbitMQMetadata;
import io.vertx.core.json.JsonObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.reactive.messaging.Acknowledgment;
import org.eclipse.microprofile.reactive.messaging.Acknowledgment.Strategy;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Outgoing;
import uk.co.rxmarkets.engine.Engine;
import uk.co.rxmarkets.engine.model.Category;
import uk.co.rxmarkets.engine.response.EngineResponse;

import javax.enterprise.context.ApplicationScoped;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;

/**
 * A bean consuming data from the "request" RabbitMQ queue and giving out a random quote.
 * The result is pushed to the "scores" RabbitMQ exchange.
 */
@ApplicationScoped
@RequiredArgsConstructor
@Slf4j
public class RequestProcessor {

    private final Engine engine;

    @Incoming("requests")
    @Outgoing("response")
    // FIXME | This should be performed concurrently, with a maximum number of consumers.
    public Uni<EngineResponse> process(JsonObject json) {
        return Uni.createFrom().item(() -> {
            final EngineRequest request = json.mapTo(EngineRequest.class);
            final EngineResponse.Builder result = new EngineResponse.Builder(request, request.getDate());
            Arrays.stream(Category.values()).forEach(c -> {
                final double score = engine.score(c.name(), request.getDataSet());
                result.addScore(c.name(), score);
            });
            log.info("Created EngineResult instance for request: {}", request);
            return result.build();
        });
    }

}

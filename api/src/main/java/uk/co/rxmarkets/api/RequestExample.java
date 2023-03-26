package uk.co.rxmarkets.api;

import io.smallrye.mutiny.Multi;
import io.vertx.core.json.JsonObject;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Outgoing;
import uk.co.rxmarkets.model.EngineRequest;

import javax.enterprise.context.ApplicationScoped;
import java.time.Duration;
import java.util.Collections;

public class RequestExample {

    public static final EngineRequest EXAMPLE_REQUEST = new EngineRequest("XLON", "CS", Collections.emptySet());

    @ApplicationScoped
    @Slf4j
    public static class Generator {

        @Outgoing("to-rabbitmq")
        public Multi<EngineRequest> doWork() {
            return Multi.createFrom().ticks().every(Duration.ofMillis(5000))
                    .map(l -> {
                        log.info("Sending EngineRequest...");
                        return EXAMPLE_REQUEST;
                    })
                    .onOverflow().drop();
        }

    }

    @ApplicationScoped
    @Slf4j
    public static class Consumer {

        @Incoming("from-rabbitmq")
        public void consume(JsonObject r) {
            final EngineRequest request = r.mapTo(EngineRequest.class);
            log.info("Received request: {}", request.toString());
        }

    }

}

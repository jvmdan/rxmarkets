package uk.co.rxmarkets.engine.response;

import io.smallrye.mutiny.Uni;
import io.vertx.core.json.JsonObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.reactive.messaging.Incoming;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
@RequiredArgsConstructor
@Slf4j
public class ResponseProcessor {

    /**
     * The "onResponse" event is triggered when the 'scores' data pipe contains a scoreboard
     * value given equity. This event results in the asynchronous persistence of the updated
     * equity to the underlying data store. The caller can choose to wait for the response,
     * or they can simply fire & forget.
     */
    @Incoming("response")
    public void onResponse(EngineResponse response) {
        log.info("Received response for request: {}", response.getRequestId());
        response.getScores().forEach((k, v) -> {
            // TODO | Now we have the response, we can persist it in the database.
            log.info("{}: {}", k, v);
        });
    }

}

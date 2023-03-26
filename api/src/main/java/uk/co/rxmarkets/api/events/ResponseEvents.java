package uk.co.rxmarkets.api.events;

import io.smallrye.mutiny.Uni;
import io.vertx.core.json.JsonObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import uk.co.rxmarkets.api.services.DatabaseService;
import uk.co.rxmarkets.model.assets.Equity;
import uk.co.rxmarkets.model.scoring.Scoreboard;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
@RequiredArgsConstructor
@Slf4j
public class ResponseEvents {

    private final DatabaseService databaseService;

    /**
     * The "onResponse" event is triggered when the 'scores' data pipe contains a scoreboard
     * value given equity. This event results in the asynchronous persistence of the updated
     * equity to the underlying data store. The caller can choose to wait for the response,
     * or they can simply fire & forget.
     *
     * @param json the response object we have received from the engine, in JSON.
     * @return the ID of the newly persisted Equity, as a Long.
     */
    @Incoming("scores")
    public Uni<Long> onResponse(JsonObject json) {
        final Scoreboard scoreboard = json.mapTo(Scoreboard.class);
        final Equity update = new Equity(123L, "XLON", "CS", true, scoreboard);
        return databaseService.createOrUpdate(update.getMarket(), update.getTicker(), update.getLatest());
    }

}

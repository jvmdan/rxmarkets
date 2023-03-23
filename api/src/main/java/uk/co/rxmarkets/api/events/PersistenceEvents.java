package uk.co.rxmarkets.api.events;

import io.quarkus.vertx.ConsumeEvent;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import uk.co.rxmarkets.api.services.PersistenceService;
import uk.co.rxmarkets.model.assets.Equity;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
@RequiredArgsConstructor
@Slf4j
public class PersistenceEvents {

    private final PersistenceService persistenceService;

    /**
     * The "persist" event is triggered by any caller that produces an updated scoreboard for a
     * given equity. This event results in the asynchronous persistence of the update equities
     * to the underlying data store. The caller can choose to wait for the response, or they
     * can simply fire & forget.
     *
     * @param equity the Equity instance we wish to persist to the database.
     * @return the ID of the persisted Equity, as a Long.
     */
    @ConsumeEvent("persist")
    public Uni<Long> persist(Equity equity) {
        log.info("Persist event triggered! {}", equity);
        return persistenceService.createOrUpdate(equity.getMarket(), equity.getTicker(), equity.getLatest());
    }

}

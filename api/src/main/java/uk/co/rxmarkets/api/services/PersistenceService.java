package uk.co.rxmarkets.api.services;

import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import uk.co.rxmarkets.model.assets.Equity;
import uk.co.rxmarkets.model.scoring.Scoreboard;

import javax.enterprise.context.ApplicationScoped;
import java.util.Objects;

@ApplicationScoped
@RequiredArgsConstructor
@Slf4j
public class PersistenceService {

    private final PgPool client;

    public Uni<Boolean> isPresent(String mic, String ticker) {
        return Equity.findByTicker(client, mic, ticker)
                .onItem().transform(Objects::nonNull);
    }

    public Uni<Long> createOrUpdate(String mic, String ticker, Scoreboard scoreboard) {
        return Equity.findByTicker(client, mic, ticker)
                .onItem().transform(equity -> (equity != null) ?
                        update(equity, scoreboard) : create(mic, ticker, scoreboard));
    }

    private Long create(String mic, String ticker, Scoreboard scoreboard) {
        final Equity equity = new Equity(-1L, mic, ticker, true, scoreboard);
        final Uni<Long> result = equity.save(client);
        return -1L; // TODO | Return the generated ID without blocking thread.
    }

    private Long update(Equity equity, Scoreboard scoreboard) {
        equity.setLatest(scoreboard);
        equity.save(client);
        return equity.getId();
    }

}

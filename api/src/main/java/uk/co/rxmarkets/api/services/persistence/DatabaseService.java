package uk.co.rxmarkets.api.services.persistence;

import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import uk.co.rxmarkets.model.assets.Equity;
import uk.co.rxmarkets.model.scoring.Scoreboard;

import javax.enterprise.context.ApplicationScoped;
import java.util.Collections;
import java.util.Objects;

@ApplicationScoped
@RequiredArgsConstructor
@Slf4j
public class DatabaseService implements PersistenceService {

    private final PgPool client;

    public Uni<Boolean> isPresent(String mic, String ticker) {
        return Equity.findByTicker(client, mic, ticker)
                .onItem().transform(Objects::nonNull);
    }

    @Override
    public Uni<Long> save(String market, String ticker, Scoreboard scoreboard) {
        return Equity.findByTicker(client, market, ticker).flatMap(equity ->
                (equity != null) ? update(equity, scoreboard) : create(market, ticker, scoreboard));
    }

    private Uni<Long> create(String mic, String ticker, Scoreboard scoreboard) {
        final Equity equity = new Equity(123L, mic, ticker, true, Collections.singletonList(scoreboard));
        return equity.save(client);
    }

    private Uni<Long> update(Equity equity, Scoreboard scoreboard) {
        equity.getScores().add(scoreboard);
        return equity.save(client);
    }

}

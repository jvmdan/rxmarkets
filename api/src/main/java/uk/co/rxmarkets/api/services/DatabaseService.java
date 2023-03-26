package uk.co.rxmarkets.api.services;

import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import uk.co.rxmarkets.model.assets.Equity;
import uk.co.rxmarkets.model.scoring.Scoreboard;

import javax.enterprise.context.ApplicationScoped;
import java.time.LocalDate;
import java.util.Objects;

@ApplicationScoped
@RequiredArgsConstructor
@Slf4j
public class DatabaseService {

    private final PgPool client;
    private final FileService fileService;

    public Uni<Boolean> isPresent(String mic, String ticker) {
        return Equity.findByTicker(client, mic, ticker)
                .onItem().transform(Objects::nonNull);
    }

    public Uni<Long> createOrUpdate(String mic, String ticker, Scoreboard scoreboard) {
        fileService.saveObjectToJson(scoreboard, ticker + "_" + LocalDate.now());
        return Equity.findByTicker(client, mic, ticker).flatMap(equity ->
                (equity != null) ? update(equity, scoreboard) : create(mic, ticker, scoreboard));
    }

    private Uni<Long> create(String mic, String ticker, Scoreboard scoreboard) {
        final Equity equity = new Equity(123L, mic, ticker, true, scoreboard);
        return equity.save(client);
    }

    private Uni<Long> update(Equity equity, Scoreboard scoreboard) {
        equity.setLatest(scoreboard);
        return equity.save(client);
    }



}

package uk.co.rxmarkets.api.services.persistence;

import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import uk.co.rxmarkets.model.assets.Equity;
import uk.co.rxmarkets.model.scoring.Scoreboard;

import javax.enterprise.context.ApplicationScoped;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ApplicationScoped
@RequiredArgsConstructor
@Slf4j
public class InMemoryService implements PersistenceService {

    private final Map<String, List<Scoreboard>> scores = new ConcurrentHashMap<>();

    private final PgPool client;

    @Override
    public Uni<Long> save(String market, String ticker, Scoreboard scoreboard) {
        return Equity.findByTicker(client, market, ticker)
                .onItem().transform(equity -> {
                    if (equity != null) {
                        scores.putIfAbsent(ticker, new LinkedList<>());
                        scores.get(ticker).add(scoreboard);
                        log.info("Added new in-memory Scoreboard instance for \"{}\"", ticker);
                    }
                    return equity != null ? equity.getId() : -1L;
                });
    }

    public List<Scoreboard> getAll(String ticker) {
        return scores.get(ticker);
    }

}

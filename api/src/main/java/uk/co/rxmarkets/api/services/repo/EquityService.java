package uk.co.rxmarkets.api.services.repo;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.reactive.mutiny.Mutiny;
import uk.co.rxmarkets.api.model.assets.Equity;
import uk.co.rxmarkets.api.model.scoring.Scoreboard;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.Locale;

@ApplicationScoped
@RequiredArgsConstructor
@Slf4j
public class EquityService implements Repository<Equity> {

    private final Mutiny.SessionFactory sf;

    public Uni<List<Equity>> findMarket(String market) {
        return sf.withTransaction((s,t) -> s
                .createNamedQuery("Equity.findMarket", Equity.class)
                .setParameter("marketId", market.toUpperCase(Locale.ROOT))
                .getResultList()
                .onItem().transformToMulti(Multi.createFrom()::iterable)
//                .onItem().call(equity -> Mutiny.fetch(equity.getScores()))
                .collect().asList()
        );
    }

    public Uni<Equity> findSingleEquity(String market, String ticker) {
        // TODO | What if there are identical tickers across different markets?
        return sf.withTransaction((s,t) -> s
                .createNamedQuery("Equity.findSingle", Equity.class)
                .setParameter("id", ticker.toUpperCase(Locale.ROOT))
                .getSingleResult()
//                .onItem().call(equity -> Mutiny.fetch(equity.getScores()))
        );
    }

    public Uni<Long> save(String market, String ticker, Scoreboard scoreboard) {
        return null;
    }

    private Uni<Long> create(String mic, String ticker, Scoreboard scoreboard) {
        return null;
    }

    private Uni<Long> update(Equity equity, Scoreboard scoreboard) {
        return null;
    }

}

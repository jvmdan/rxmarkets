package uk.co.rxmarkets.api.services.repo;

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

    public Uni<List<Equity>> findAll() {
        return sf.withTransaction((s,t) -> s
                .createNamedQuery("Equity.findAll", Equity.class)
                .getResultList()
        );
    }

    public Uni<List<Equity>> findMarket(String market) {
        return sf.withTransaction((s,t) -> s
                .createNamedQuery("Equity.findMarket", Equity.class)
                .setParameter("market", market.toUpperCase(Locale.ROOT))
                .getResultList()
        );
    }

    public Uni<Equity> findSingleEquity(String market, String ticker) {
        return sf.withTransaction((s,t) -> s
                .createNamedQuery("Equity.findSingle", Equity.class)
                .setParameter("market", market.toUpperCase(Locale.ROOT))
                .setParameter("ticker", ticker.toUpperCase(Locale.ROOT))
                .getSingleResult()
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

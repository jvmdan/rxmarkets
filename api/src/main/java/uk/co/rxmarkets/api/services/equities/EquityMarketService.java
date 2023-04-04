package uk.co.rxmarkets.api.services.equities;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.reactive.mutiny.Mutiny;
import uk.co.rxmarkets.api.model.markets.EquityMarket;
import uk.co.rxmarkets.api.services.Repository;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
@RequiredArgsConstructor
@Slf4j
public class EquityMarketService implements Repository<EquityMarket> {

    private final Mutiny.SessionFactory sf;

    public Uni<List<EquityMarket>> findAll() {
        return sf.withTransaction((s,t) -> s
                .createNamedQuery("EquityMarket.findAll", EquityMarket.class)
                .getResultList()
                .onItem().transformToMulti(Multi.createFrom()::iterable)
                .onItem().call(market -> Mutiny.fetch(market.getEquities()))
                .collect().asList()
        );
    }

}

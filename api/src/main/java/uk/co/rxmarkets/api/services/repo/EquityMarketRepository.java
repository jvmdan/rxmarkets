package uk.co.rxmarkets.api.services.repo;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.reactive.mutiny.Mutiny;
import uk.co.rxmarkets.api.model.markets.EquityMarket;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.util.List;

import static javax.ws.rs.core.Response.Status.*;

@ApplicationScoped
@RequiredArgsConstructor
@Slf4j
public class EquityMarketRepository implements Repository<String, EquityMarket> {

    private final Mutiny.SessionFactory sf;

    @Override
    public Uni<List<EquityMarket>> findAll() {
        return sf.withTransaction((s,t) -> s
                .createNamedQuery("EquityMarket.findAll", EquityMarket.class)
                .getResultList()
                .onItem().transformToMulti(Multi.createFrom()::iterable)
                .onItem().call(market -> Mutiny.fetch(market.getEquities()))
                .collect().asList()
        );
    }
    @Override
    public Uni<EquityMarket> findById(String id) {
        return sf.withTransaction((s, t) -> s.find(EquityMarket.class, id)
                .onItem().ifNull().failWith(new WebApplicationException("EquityMarket missing from database.", NOT_FOUND)));
    }

    @Override
    public Uni<Response> create(EquityMarket entity) {
        if (entity == null || entity.getId() != null) {
            throw new WebApplicationException("ID shall not be set on request.", 422);
        }
        return sf.withTransaction((s, t) -> s.persist(entity))
                .replaceWith(Response.ok(entity).status(CREATED)::build);
    }

    @Override
    public Uni<Response> delete(String id) {
        return sf.withTransaction((s, t) -> s.find(EquityMarket.class, id)
                        .onItem().ifNull().failWith(new WebApplicationException("EquityMarket missing from database.", NOT_FOUND))
                        .call(s::remove))
                .replaceWith(Response.ok().status(NO_CONTENT)::build);
    }

}

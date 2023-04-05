package uk.co.rxmarkets.api.services.repo;

import io.smallrye.mutiny.Uni;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.reactive.mutiny.Mutiny;
import uk.co.rxmarkets.api.entities.Equity;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Locale;

import static javax.ws.rs.core.Response.Status.*;

@ApplicationScoped
@RequiredArgsConstructor
@Slf4j
public class EquityRepository implements Repository<String, Equity> {

    private final Mutiny.SessionFactory sf;

    @Override
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


    @Override
    public Uni<Equity> findById(String ticker) {
        return sf.withTransaction((s, t) -> s.find(Equity.class, ticker)
                .onItem().ifNull().failWith(new WebApplicationException("Equity missing from database.", NOT_FOUND)));
    }

    @Override
    public Uni<Response> create(Equity entity) {
        if (entity == null || entity.getTicker() != null) {
            throw new WebApplicationException("ID shall not be set on request.", 422);
        }
        return sf.withTransaction((s, t) -> s.persist(entity))
                .replaceWith(Response.ok(entity).status(CREATED)::build);
    }

    @Override
    public Uni<Response> delete(String id) {
        return sf.withTransaction((s, t) -> s.find(Equity.class, id)
                        .onItem().ifNull().failWith(new WebApplicationException("Equity missing from database.", NOT_FOUND))
                        .call(s::remove))
                .replaceWith(Response.ok().status(NO_CONTENT)::build);
    }

}

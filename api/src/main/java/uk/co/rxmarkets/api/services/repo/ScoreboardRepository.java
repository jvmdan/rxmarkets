package uk.co.rxmarkets.api.services.repo;

import io.smallrye.mutiny.Uni;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.reactive.mutiny.Mutiny;
import uk.co.rxmarkets.api.entities.Equity;
import uk.co.rxmarkets.api.entities.Scoreboard;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.util.List;

import static javax.ws.rs.core.Response.Status.*;

@ApplicationScoped
@RequiredArgsConstructor
@Slf4j
public class ScoreboardRepository implements Repository<String, Scoreboard> {

    private final Mutiny.SessionFactory sf;

    @Override
    public Uni<List<Scoreboard>> findAll() {
        return sf.withTransaction((s,t) -> s
                .createNamedQuery("Scoreboard.findAll", Scoreboard.class)
                .getResultList()
        );
    }

    public Uni<List<Scoreboard>> findByEquity(Equity equity) {
        return sf.withTransaction((s,t) -> s
                .createNamedQuery("Scoreboard.findForEquity", Scoreboard.class)
                .setParameter("ticker", equity.getTicker())
                .getResultList()
        );
    }

    @Override
    public Uni<Scoreboard> findById(String uuid) {
        return sf.withTransaction((s, t) -> s.find(Scoreboard.class, uuid)
                .onItem().ifNull().failWith(new WebApplicationException("Scoreboard missing from database.", NOT_FOUND)));
    }

    @Override
    public Uni<Response> create(Scoreboard entity) {
        if (entity == null || entity.getUuid() != null) {
            throw new WebApplicationException("UUID shall not be set on request.", 422);
        }
        return sf.withTransaction((s, t) -> s.persist(entity))
                .replaceWith(Response.ok(entity).status(CREATED)::build);
    }

    @Override
    public Uni<Response> delete(String uuid) {
        return sf.withTransaction((s, t) -> s.find(Scoreboard.class, uuid)
                        .onItem().ifNull().failWith(new WebApplicationException("Scoreboard missing from database.", NOT_FOUND))
                        .call(s::remove))
                .replaceWith(Response.ok().status(NO_CONTENT)::build);
    }

}

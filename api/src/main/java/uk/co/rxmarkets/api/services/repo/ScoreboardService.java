package uk.co.rxmarkets.api.services.repo;

import io.smallrye.mutiny.Uni;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.reactive.mutiny.Mutiny;
import uk.co.rxmarkets.api.model.assets.Equity;
import uk.co.rxmarkets.api.model.scoring.Scoreboard;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
@RequiredArgsConstructor
@Slf4j
public class ScoreboardService implements Repository<Scoreboard> {

    private final Mutiny.SessionFactory sf;

    public Uni<List<Scoreboard>> findByEquity(Equity equity) {
        return sf.withTransaction((s,t) -> s
                .createNamedQuery("Scoreboard.findForEquity", Scoreboard.class)
                .setParameter("equityId", equity.getId())
                .getResultList()
        );
    }

}

package uk.co.rxmarkets.api.services.persistence;

import io.smallrye.mutiny.Uni;
import uk.co.rxmarkets.model.scoring.Scoreboard;

public interface PersistenceService {

    Uni<Long> save(String market, String ticker, Scoreboard scoreboard);

}

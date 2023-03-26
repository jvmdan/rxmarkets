package uk.co.rxmarkets.api.services;

import lombok.extern.slf4j.Slf4j;
import uk.co.rxmarkets.model.ranking.Ranked;
import uk.co.rxmarkets.model.scoring.Category;
import uk.co.rxmarkets.model.scoring.Scoreboard;

import javax.enterprise.context.ApplicationScoped;
import java.util.Arrays;
import java.util.Set;

@ApplicationScoped
@Slf4j
public class EngineService {

//    private final Engine<Category, Ranked> engine = new OpenAiEngine();

    public Scoreboard evaluate(Set<Ranked> dataSet) {
        final Scoreboard.Builder scoreboard = new Scoreboard.Builder();
        Arrays.stream(Category.values()).forEach(c -> {
//            final Indicator score = engine.score(c, dataSet);
//            scoreboard.addScore(c, score);
        });
        return scoreboard.build();
    }

}

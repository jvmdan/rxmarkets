package uk.co.rxmarkets.api;

import uk.co.rxmarkets.engine.RandomEngine;
import uk.co.rxmarkets.model.Engine;
import uk.co.rxmarkets.model.ranking.Ranked;
import uk.co.rxmarkets.model.scoring.Category;
import uk.co.rxmarkets.model.scoring.Indicator;

import javax.enterprise.context.ApplicationScoped;
import java.util.Set;

@ApplicationScoped
public class EngineService {

    private final Engine<Category, Ranked> engine = new RandomEngine();

    public Indicator evaluate(Category category, Set<Ranked> ranked) {
        final double score = engine.score(category, ranked);
        final int confidence = engine.confidence(category, ranked);
        return new Indicator(score, confidence);
    }

}

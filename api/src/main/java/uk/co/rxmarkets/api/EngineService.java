package uk.co.rxmarkets.api;

import lombok.extern.slf4j.Slf4j;
import uk.co.rxmarkets.engine.DefaultEngine;
import uk.co.rxmarkets.engine.RandomEngine;
import uk.co.rxmarkets.model.Engine;
import uk.co.rxmarkets.model.ranking.Ranked;
import uk.co.rxmarkets.model.scoring.Category;
import uk.co.rxmarkets.model.scoring.Indicator;

import javax.enterprise.context.ApplicationScoped;
import java.util.Set;

@ApplicationScoped
@Slf4j
public class EngineService {

    private final Engine<Category, Ranked> engine = new RandomEngine();

    public Indicator evaluate(Category category, Set<Ranked> ranked) {
        final double score = engine.score(category, ranked);
        final int confidence = engine.confidence(category, ranked);
        log.info("Evaluated {} using {} data points [score={}, confidence={}]", category.name(), ranked.size(), score, confidence);
        return new Indicator(score, confidence);
    }

}

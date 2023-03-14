package uk.co.rxmarkets.api;

import lombok.RequiredArgsConstructor;
import uk.co.rxmarkets.model.Engine;
import uk.co.rxmarkets.model.ranking.Ranked;
import uk.co.rxmarkets.model.scoring.Category;
import uk.co.rxmarkets.model.scoring.Indicator;

import javax.enterprise.context.ApplicationScoped;
import java.util.Set;

@ApplicationScoped
@RequiredArgsConstructor
public class EngineService {

    private final Engine<Category, Ranked> engine;

    public Indicator evaluate(Category category, Set<Ranked> ranked) {
        final double score = engine.score(category, ranked);
        final int confidence = engine.confidence(category, ranked);
        return new Indicator(score, confidence);
    }

}

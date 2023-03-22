package uk.co.rxmarkets.engine;

import lombok.extern.slf4j.Slf4j;
import uk.co.rxmarkets.model.Engine;
import uk.co.rxmarkets.model.ranking.Ranked;
import uk.co.rxmarkets.model.scoring.Category;

import java.util.Set;

/**
 * The DefaultEngine shall be the main point of contact to the underlying Natural-Language
 * Processing (NLP) intelligence. This will provide an interface for communicating
 * with the AI and scoring our dataset based upon the response.
 *
 * @author Daniel Scarfe
 */
@Slf4j
public class DefaultEngine implements Engine<Category, Ranked> {

    @Override
    public double score(Category category, Set<Ranked> ranked) {
        log.info("Scoring {} using {} data points...", category.name(), ranked.size());
        return 0;
    }

    @Override
    public int confidence(Category category, Set<Ranked> ranked) {
        log.info("Ascertaining confidence in {} score using {} data points...", category.name(), ranked.size());
        return 0;
    }

}

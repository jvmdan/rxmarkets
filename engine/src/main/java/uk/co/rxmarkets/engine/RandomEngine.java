package uk.co.rxmarkets.engine;

import uk.co.rxmarkets.model.Engine;
import uk.co.rxmarkets.model.ranking.Ranked;
import uk.co.rxmarkets.model.scoring.Category;
import uk.co.rxmarkets.model.scoring.Indicator;

import java.util.Set;

/**
 * Useful in test & validation, the RandomEngine ignores the given dataset and produces
 * entirely random scores & confidence levels on every call. This allows you to
 * produce a completely different Scoreboard each time.
 *
 * @author Daniel Scarfe
 */
public class RandomEngine implements Engine<Category, Ranked> {

    @Override
    public Indicator score(Category category, Set<Ranked> ranked) {
        // Produces an entirely random score for the given category.
        final double scale = Math.pow(10, 5);
        final double score = Math.round(Math.random() * scale) / scale;
        final int confidence = (int) Math.floor(Math.random() * 10);
        return new Indicator(score, confidence);
    }

}
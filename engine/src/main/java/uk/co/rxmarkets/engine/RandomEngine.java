package uk.co.rxmarkets.engine;

import uk.co.rxmarkets.api.model.Engine;
import uk.co.rxmarkets.api.model.ranking.Ranked;
import uk.co.rxmarkets.api.model.scoring.Category;
import uk.co.rxmarkets.api.model.scoring.Indicator;

import javax.enterprise.context.ApplicationScoped;
import java.util.Set;

/**
 * Useful in test & validation, the RandomEngine ignores the given dataset and produces
 * entirely random scores & confidence levels on every call. This allows you to
 * produce a completely different Scoreboard each time.
 *
 * @author Daniel Scarfe
 */
@ApplicationScoped
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
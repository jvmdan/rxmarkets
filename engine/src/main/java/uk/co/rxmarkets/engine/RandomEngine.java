package uk.co.rxmarkets.engine;

import uk.co.rxmarkets.engine.model.Ranked;

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
public class RandomEngine implements Engine {

    @Override
    public double score(String category, Set<Ranked> data) {
        final double scale = Math.pow(10, 5);
        return Math.round(Math.random() * scale) / scale;
    }

}
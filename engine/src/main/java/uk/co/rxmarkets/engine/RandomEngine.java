package uk.co.rxmarkets.engine;

import uk.co.rxmarkets.model.Engine;
import uk.co.rxmarkets.model.ranking.Ranked;
import uk.co.rxmarkets.model.scoring.Category;

import java.util.Set;

public class RandomEngine implements Engine<Category, Ranked> {

    @Override
    public double score(Category category, Set<Ranked> ranked) {
        // Produces an entirely random score for the given category.
        final double scale = Math.pow(10, 5);
        return Math.round(Math.random() * scale) / scale;
    }

    @Override
    public int confidence(Category category, Set<Ranked> ranked) {
        // Produces an entirely random confidence for the given category.
        return (int) Math.floor(Math.random() * 10);
    }

}
package uk.co.rxmarkets.engine;

import lombok.extern.slf4j.Slf4j;
import uk.co.rxmarkets.model.Engine;
import uk.co.rxmarkets.model.ranking.Ranked;
import uk.co.rxmarkets.model.scoring.Category;
import uk.co.rxmarkets.model.scoring.Indicator;

import java.util.HashMap;
import java.util.Map;
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
    public Indicator score(Category category, Set<Ranked> ranked) {
        // Calculate the lower & upper scoring bounds for our data set.
        final int dataSize = ranked.size();
        final Map<Ranked, Impact> weightedData = new HashMap<>(dataSize);
        final int lowerBounds = Impact.STRONGLY_NEGATIVE.getWeight() * dataSize;
        final int upperBounds = Impact.STRONGLY_POSITIVE.getWeight() * dataSize;

        // TODO | Assess the impact of each piece of data.
        ranked.forEach(opinion -> {
            final Impact impact = Impact.NEUTRAL;
            weightedData.put(opinion, impact);
        });

        // Normalise our weighted score between the value of 0 and 1.
        final int count = weightedData.values().stream().mapToInt(Impact::getWeight).sum();
        final double score = percentage(count, lowerBounds, upperBounds);

        // Determine how dispersed our data is from the mean value.
        final double[] impacts = weightedData.values().stream().mapToDouble(Impact::getWeight).toArray();
        final int confidence = (int) (10 - standardDeviation(impacts));

        log.info("{}[score={}, confidence={}]", category, score, confidence);
        return new Indicator(score, confidence);
    }

    /*
     * A package-scoped helper method that calculates the percentage of value 'v' in
     * relation to the minimum & maximum values. For example, if the lowest possible
     * score is 10 and the highest score is 100, then the value of 55 represents
     * exactly halfway between these two numbers (and so the function returns 0.5).
     */
    static double percentage(double v, double min, double max) {
        return (v - min) / (max - min);
    }

    /*
     * A package-scoped helper method that calculates the standard deviation of a
     * given array of values. Low standard deviation means data are clustered around
     * the mean, and high standard deviation indicates data are more spread out.
     */
    static double standardDeviation(double[] values) {
        double sum = 0.0, deviation = 0.0;
        for (double v : values) sum += v;
        double mean = sum / values.length;
        for (double v : values) deviation += Math.pow(v - mean, 2);
        return Math.sqrt(deviation / values.length);
    }

}

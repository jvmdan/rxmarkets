package uk.co.rxmarkets.model.scoring;

/**
 * An Indicator is an immutable object tying a score value to a sentiment category.
 * The value of the score shall always been between 0 and 1, where 'zero' represents
 * very negative market sentiment and 'one' represents very positive. A single asset
 * shall have an indicator for every category, for every date available.
 * <p/>
 * Note: We also attribute a confidence rating to our Indicator instance. This
 * represents how confident we are in the score given, in the range of 1 to 10,
 * where 10 represents absolute confidence (and is in practice unachievable). The
 * confidence rating is determined by the amount of data available; that is both
 * the number of sources & the number of opinions used to determine the score value.
 *
 * @param score      the sentiment value, in the range of 0-1
 * @param category   the category of market sentiment we have evaluated
 * @param confidence the amount of confidence we have in the score (range 1-10)
 * @author Daniel Scarfe
 */
public record Indicator(Category category, double score, int confidence) {

    public static Indicator reset(Category c) {
        return new Indicator(c, 0, 0);
    }

}
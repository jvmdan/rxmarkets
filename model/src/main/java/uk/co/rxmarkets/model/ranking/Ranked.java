package uk.co.rxmarkets.model.ranking;

/**
 * The Ranked interface represents any piece of data which can affect the scoring
 * of market sentiment.
 *
 * @author Daniel Scarfe
 */
public interface Ranked extends Comparable<Impact> {

    Impact getImpact();

    @Override
    default int compareTo(Impact other) {
        return this.getImpact().compareTo(other);
    }

}

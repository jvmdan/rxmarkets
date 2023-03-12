package uk.co.rxmarkets.model.ranking;

import lombok.Getter;
import uk.co.rxmarkets.model.sources.Source;

/**
 * A Fact represents the smallest piece of data available within the system. Typically,
 * facts are numerical, and some examples include the results of the Volatility Index
 * (VIX), Commitment of Traders (COT), put/call ratio and moving averages.
 *
 * @param source the parent source of the factual data.
 * @param data   the real-world content, taken directly from the source.
 * @param impact the impact of our data; is the fact positive or negative?
 * @author Daniel Scarfe
 * @see Opinion
 */
@Getter
public record Fact(Source source, Number data, Impact impact) implements Ranked {
}

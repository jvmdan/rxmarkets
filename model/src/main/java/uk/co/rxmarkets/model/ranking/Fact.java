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
 * @author Daniel Scarfe
 * @see Opinion
 */
public record Fact(Source source, String data) implements Ranked {

    @Override
    public String getData() {
        return data;
    }

}

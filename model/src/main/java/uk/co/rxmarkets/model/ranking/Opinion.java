package uk.co.rxmarkets.model.ranking;

import uk.co.rxmarkets.model.sources.Source;

/**
 * An Opinion represents the smallest piece of data within the system. This may be
 * the content of a news article, a comment on social media, a user review or any
 * other form of information that is based upon human thoughts & feelings.
 *
 * @param source the parent source of the opinion data.
 * @param data   the real-world content, taken directly from the source.
 * @author Daniel Scarfe
 * @see Fact
 */
public record Opinion(String source, String data) implements Ranked {

}

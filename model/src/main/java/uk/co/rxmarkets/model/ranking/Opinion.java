package uk.co.rxmarkets.model.ranking;

import lombok.Getter;
import uk.co.rxmarkets.model.sources.Source;

/**
 * An Opinion represents the smallest piece of data within the system. This may be
 * the content of a news article, a comment on social media, a user review or any
 * other form of information that is based upon human thoughts & feelings.
 *
 * @param source the parent source of the opinion data.
 * @param data   the real-world content, taken directly from the source.
 * @param impact the impact of our data; is the opinion positive or negative?
 * @author Daniel Scarfe
 * @see Fact
 */
@Getter
public record Opinion(Source source, String data, Impact impact) implements Ranked {

}

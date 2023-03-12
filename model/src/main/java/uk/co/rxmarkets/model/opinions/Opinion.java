package uk.co.rxmarkets.model.opinions;

import uk.co.rxmarkets.model.source.Source;

/**
 * An Opinion represents the smallest piece of data within the system. This may be
 * the content of a news article, a comment on social media, a user review or
 * more.
 * 
 * @param source
 * @param data
 * @param classification
 */
public record Opinion(Source source, String data, Classification classification) {

}

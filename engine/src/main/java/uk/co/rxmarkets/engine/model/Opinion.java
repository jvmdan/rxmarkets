package uk.co.rxmarkets.engine.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * An Opinion represents the smallest piece of data within the system. This may be
 * the content of a news article, a comment on social media, a user review or any
 * other form of information that is based upon human thoughts & feelings.
 *
 * @author Daniel Scarfe
 */
@Data
@AllArgsConstructor
public class Opinion implements Ranked {

    private final String source;
    private final String data;

    @Override
    public String getData() {
        return data;
    }

}

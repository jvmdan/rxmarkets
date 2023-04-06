package uk.co.rxmarkets.engine;

import uk.co.rxmarkets.engine.model.Opinion;

import java.util.Set;

/**
 * The Engine interface represents the entry-point to the proprietary analysis
 * engine. This interface describes the public API contract which allows us
 * to trigger sentiment analysis on a given asset for a given category.
 *
 * @author Daniel Scarfe
 */
public interface Engine {

    double score(String category, Set<Opinion> data);

}

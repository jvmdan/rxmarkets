package uk.co.rxmarkets.api.model;

import uk.co.rxmarkets.api.model.ranking.Ranked;
import uk.co.rxmarkets.api.model.scoring.Category;
import uk.co.rxmarkets.api.model.scoring.Indicator;

import java.util.Set;

/**
 * The Engine interface represents the entry-point to the proprietary analysis
 * engine. This interface describes the public API contract which allows us
 * to trigger sentiment analysis on a given asset for a given category.
 *
 * @param <C> the category / categories the engine is capable of evaluating
 * @param <R> the type of ranked data the engine is capable of evaluating
 * @author Daniel Scarfe
 */
public interface Engine<C extends Category, R extends Ranked> {

    Indicator score(C category, Set<R> ranked);

}

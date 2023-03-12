package uk.co.rxmarkets.model.sources;

import uk.co.rxmarkets.model.ranking.Ranked;

import java.util.Set;

public interface Source {

    Set<Ranked> getRankedSet();

}

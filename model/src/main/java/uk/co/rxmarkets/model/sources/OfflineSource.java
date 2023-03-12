package uk.co.rxmarkets.model.sources;

import lombok.Getter;
import uk.co.rxmarkets.model.ranking.Ranked;

import java.net.URI;
import java.util.Set;
import java.util.TreeSet;

@Getter
public abstract class OfflineSource implements Source {

    private final Set<Ranked> rankedSet = new TreeSet<>();

    URI getUri;

}

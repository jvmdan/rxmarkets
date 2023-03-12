package uk.co.rxmarkets.model.sources;

import lombok.Getter;
import uk.co.rxmarkets.model.ranking.Ranked;

import java.net.URL;
import java.util.Set;
import java.util.TreeSet;

@Getter
public abstract class OnlineSource implements Source {

    private final Set<Ranked> rankedSet = new TreeSet<>();

    protected abstract URL getUrl();

}

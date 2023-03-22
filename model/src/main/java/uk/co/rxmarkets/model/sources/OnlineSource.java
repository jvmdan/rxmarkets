package uk.co.rxmarkets.model.sources;

import java.net.URL;

public abstract class OnlineSource implements Source {

    protected abstract URL getUrl();

}

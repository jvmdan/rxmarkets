package uk.co.rxmarkets.model.sources;

import java.net.URI;

public abstract class OfflineSource implements Source {

    protected abstract URI getUri();

}

package uk.co.rxmarkets.model.markets;

import uk.co.rxmarkets.model.assets.Asset;

public interface Market<A extends Asset> {

    enum Location {
        US, UK, CN;
    }

    Class<A> getType();

}

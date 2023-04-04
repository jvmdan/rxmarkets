package uk.co.rxmarkets.api.model.markets;

import uk.co.rxmarkets.api.model.assets.Asset;

public interface Market<T extends Asset> {

    enum Location {
        US, UK, CN;
    }

}

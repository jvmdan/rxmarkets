package uk.co.rxmarkets.api.entities.markets;

import uk.co.rxmarkets.api.entities.assets.Asset;

public interface Market<T extends Asset> {

    enum Location {
        US, UK, CN;
    }

}

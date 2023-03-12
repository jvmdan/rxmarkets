package uk.co.rxmarkets.model.markets;

import uk.co.rxmarkets.model.assets.Asset;

public interface Market<A extends Asset> {

    Class<A> getType();

}

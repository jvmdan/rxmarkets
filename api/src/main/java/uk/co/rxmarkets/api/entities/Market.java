package uk.co.rxmarkets.api.entities;

public interface Market<T extends Asset> {

    enum Location {
        US, UK, CN;
    }

}

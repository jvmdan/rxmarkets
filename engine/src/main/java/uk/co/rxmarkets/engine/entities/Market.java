package uk.co.rxmarkets.engine.entities;

public interface Market<T extends Asset> {

    enum Location {
        US, UK, CN;
    }

}

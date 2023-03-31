package uk.co.rxmarkets.api.configuration;

import io.quarkus.runtime.StartupEvent;

import javax.enterprise.event.Observes;

public interface Configuration {

    void initialise(@Observes StartupEvent ev);

}

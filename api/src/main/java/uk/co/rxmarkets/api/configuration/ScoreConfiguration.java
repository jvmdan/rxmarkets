package uk.co.rxmarkets.api.configuration;

import io.quarkus.runtime.StartupEvent;
import io.vertx.mutiny.pgclient.PgPool;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;

@ApplicationScoped
public class ScoreConfiguration implements Configuration {

    private final PgPool client;
    private final boolean schemaCreate;

    public ScoreConfiguration(PgPool client, @ConfigProperty(name = "scores.schema.create", defaultValue = "true") boolean schemaCreate) {
        this.client = client;
        this.schemaCreate = schemaCreate;
    }

    @Override
    public void initialise(@Observes StartupEvent ev) {
        if (schemaCreate) {
            // TODO | Read the JSON out of the resources folder, and pre-populate the scoreboard.
        }
    }

}

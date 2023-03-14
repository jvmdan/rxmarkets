package uk.co.rxmarkets.api.database;

import io.quarkus.runtime.StartupEvent;
import io.vertx.mutiny.pgclient.PgPool;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;

@ApplicationScoped
public class MarketConfiguration {

    private final PgPool client;
    private final boolean schemaCreate;

    public MarketConfiguration(PgPool client, @ConfigProperty(name = "markets.schema.create", defaultValue = "true") boolean schemaCreate) {
        this.client = client;
        this.schemaCreate = schemaCreate;
    }

    void initialise(@Observes StartupEvent ev) {
        if (schemaCreate) {
            client.query("DROP TABLE IF EXISTS markets").execute()
                    .flatMap(r -> client.query("CREATE TABLE markets (id SERIAL PRIMARY KEY, mic TEXT NOT NULL, name TEXT NOT NULL, location TEXT NOT NULL)").execute())
                    .flatMap(r -> client.query("INSERT INTO markets (id, mic, name, location) VALUES (1, 'XNAS', 'NASDAQ', 'US')").execute())
                    .flatMap(r -> client.query("INSERT INTO markets (id, mic, name, location) VALUES (2, 'XLON', 'London Stock Exchange (LSE)', 'UK')").execute())
                    .flatMap(r -> client.query("INSERT INTO markets (id, mic, name, location) VALUES (3, 'XNYS', 'New York Stock Exchange (NYSE)', 'US')").execute())
                    .await().indefinitely();
        }
    }

}

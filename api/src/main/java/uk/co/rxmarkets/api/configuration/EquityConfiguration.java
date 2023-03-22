package uk.co.rxmarkets.api.configuration;

import io.quarkus.runtime.StartupEvent;
import io.vertx.mutiny.pgclient.PgPool;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;

@ApplicationScoped
public class EquityConfiguration {

    private final PgPool client;
    private final boolean schemaCreate;

    public EquityConfiguration(PgPool client, @ConfigProperty(name = "equities.schema.create", defaultValue = "true") boolean schemaCreate) {
        this.client = client;
        this.schemaCreate = schemaCreate;
    }

    void initialise(@Observes StartupEvent ev) {
        if (schemaCreate) {
            client.query("DROP TABLE IF EXISTS equities").execute()
                    .flatMap(r -> client.query("CREATE TABLE equities (id SERIAL PRIMARY KEY, market TEXT NOT NULL, ticker TEXT NOT NULL)").execute())
                    .flatMap(r -> client.query("INSERT INTO equities (id, market, ticker) VALUES (1, 'XNAS', 'AAPL')").execute())
                    .flatMap(r -> client.query("INSERT INTO equities (id, market, ticker) VALUES (2, 'XNAS', 'TSLA')").execute())
                    .flatMap(r -> client.query("INSERT INTO equities (id, market, ticker) VALUES (3, 'XNAS', 'AMZN')").execute())
                    .flatMap(r -> client.query("INSERT INTO equities (id, market, ticker) VALUES (4, 'XNAS', 'GOOG')").execute())
                    .flatMap(r -> client.query("INSERT INTO equities (id, market, ticker) VALUES (5, 'XNAS', 'MSFT')").execute())
                    .flatMap(r -> client.query("INSERT INTO equities (id, market, ticker) VALUES (6, 'XLON', 'LLOY')").execute())
                    .flatMap(r -> client.query("INSERT INTO equities (id, market, ticker) VALUES (7, 'XLON', 'BARC')").execute())
                    .flatMap(r -> client.query("INSERT INTO equities (id, market, ticker) VALUES (8, 'XLON', 'SHEL')").execute())
                    .flatMap(r -> client.query("INSERT INTO equities (id, market, ticker) VALUES (9, 'XNYS', 'JPM')").execute())
                    .flatMap(r -> client.query("INSERT INTO equities (id, market, ticker) VALUES (10, 'XNYS', 'CS')").execute())
                    .await().indefinitely();
        }
    }

}

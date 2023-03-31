package uk.co.rxmarkets.api.configuration;

import io.quarkus.runtime.StartupEvent;
import io.vertx.mutiny.pgclient.PgPool;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;

@ApplicationScoped
public class EquityConfiguration implements Configuration {

    private static final String EQUITY_SCHEMA = "CREATE TABLE equities (" +
            "id SERIAL PRIMARY KEY, " +
            "market TEXT NOT NULL, " +
            "ticker TEXT NOT NULL, " +
            "active BOOLEAN NOT NULL " +
            ")";

    private final PgPool client;
    private final boolean schemaCreate;

    public EquityConfiguration(PgPool client, @ConfigProperty(name = "equities.schema.create", defaultValue = "true") boolean schemaCreate) {
        this.client = client;
        this.schemaCreate = schemaCreate;
    }

    @Override
    public void initialise(@Observes StartupEvent ev) {
        if (schemaCreate) {
            client.query("DROP TABLE IF EXISTS equities").execute()
                    .flatMap(r -> client.query(EQUITY_SCHEMA).execute())
                    .flatMap(r -> client.query("INSERT INTO equities (market, ticker, active) VALUES ('XNAS', 'AAPL', true)").execute())
                    .flatMap(r -> client.query("INSERT INTO equities (market, ticker, active) VALUES ('XNAS', 'TSLA', true)").execute())
                    .flatMap(r -> client.query("INSERT INTO equities (market, ticker, active) VALUES ('XNAS', 'AMZN', true)").execute())
                    .flatMap(r -> client.query("INSERT INTO equities (market, ticker, active) VALUES ('XNAS', 'GOOG', true)").execute())
                    .flatMap(r -> client.query("INSERT INTO equities (market, ticker, active) VALUES ('XNAS', 'MSFT', true)").execute())
                    .flatMap(r -> client.query("INSERT INTO equities (market, ticker, active) VALUES ('XLON', 'LLOY', true)").execute())
                    .flatMap(r -> client.query("INSERT INTO equities (market, ticker, active) VALUES ('XLON', 'BARC', true)").execute())
                    .flatMap(r -> client.query("INSERT INTO equities (market, ticker, active) VALUES ('XLON', 'SHEL', true)").execute())
                    .flatMap(r -> client.query("INSERT INTO equities (market, ticker, active) VALUES ('XNYS', 'JPM', true)").execute())
                    .await().indefinitely();
        }
    }

}

package uk.co.rxmarkets.api.equities;

import io.quarkus.runtime.StartupEvent;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import uk.co.rxmarkets.model.assets.Equity;

import javax.enterprise.event.Observes;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;
import java.net.URI;

@Path("api/equities")
public class EquityResource {

    private final PgPool client;
    private final boolean schemaCreate;

    public EquityResource(PgPool client, @ConfigProperty(name = "equities.schema.create", defaultValue = "true") boolean schemaCreate) {
        this.client = client;
        this.schemaCreate = schemaCreate;
    }

    void initialise(@Observes StartupEvent ev) {
        if (schemaCreate) {
            client.query("DROP TABLE IF EXISTS equities").execute()
                    .flatMap(r -> client.query("CREATE TABLE equities (id SERIAL PRIMARY KEY, ticker TEXT NOT NULL)")
                            .execute())
                    .flatMap(r -> client.query("INSERT INTO equities (ticker) VALUES ('AAPL')").execute())
                    .flatMap(r -> client.query("INSERT INTO equities (ticker) VALUES ('GOOG')").execute())
                    .flatMap(r -> client.query("INSERT INTO equities (ticker) VALUES ('MSFT')").execute())
                    .await().indefinitely();
        }
    }

    @GET
    public Uni<Response> get() {
        return Equity.findAll(client)
                .onItem().transform(Response::ok)
                .onItem().transform(ResponseBuilder::build);
    }

    @GET
    @Path("{id}")
    public Uni<Response> getSingle(Long id) {
        return Equity.findById(client, id)
                .onItem().transform(equity -> equity != null ? Response.ok(equity) : Response.status(Status.NOT_FOUND))
                .onItem().transform(ResponseBuilder::build);
    }

    @POST
    public Uni<Response> create(Equity equity) {
        return equity.save(client)
                .onItem().transform(id -> URI.create("api/equities/" + id))
                .onItem().transform(uri -> Response.created(uri).build());
    }

    @PUT
    @Path("{id}")
    public Uni<Response> update(Long id, Equity equity) {
        return equity.update(client)
                .onItem().transform(updated -> updated ? Status.OK : Status.NOT_FOUND)
                .onItem().transform(status -> Response.status(status).build());
    }

    @DELETE
    @Path("{id}")
    public Uni<Response> delete(Long id) {
        return Equity.delete(client, id)
                .onItem().transform(deleted -> deleted ? Status.NO_CONTENT : Status.NOT_FOUND)
                .onItem().transform(status -> Response.status(status).build());
    }

}

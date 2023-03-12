package uk.co.rxmarkets.api.equities;

import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import lombok.RequiredArgsConstructor;
import uk.co.rxmarkets.model.assets.Equity;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;
import java.net.URI;

@Path("api/equities")
@RequiredArgsConstructor
public class EquityResource {

    private final PgPool client;

    @GET
    public Uni<Response> all() {
        return Equity.findAll(client)
                .onItem().transform(Response::ok)
                .onItem().transform(ResponseBuilder::build);
    }

    @GET
    @Path("{market}")
    public Uni<Response> getMarket(String market) {
        return Equity.findByMarket(client, market)
                .onItem().transform(Response::ok)
                .onItem().transform(ResponseBuilder::build);
    }

    @GET
    @Path("{market}/{id}")
    public Uni<Response> getSingle(String market, Long id) {
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
    @Path("{market}/{id}")
    public Uni<Response> update(Long id, Equity equity) {
        return equity.update(client)
                .onItem().transform(updated -> updated ? Status.OK : Status.NOT_FOUND)
                .onItem().transform(status -> Response.status(status).build());
    }

    @DELETE
    @Path("{market}/{id}")
    public Uni<Response> delete(Long id) {
        // TODO | Useful in testing but should not be available in public API.
        return Equity.delete(client, id)
                .onItem().transform(deleted -> deleted ? Status.NO_CONTENT : Status.NOT_FOUND)
                .onItem().transform(status -> Response.status(status).build());
    }

}

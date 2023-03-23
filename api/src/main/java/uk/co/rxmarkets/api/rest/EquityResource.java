package uk.co.rxmarkets.api.rest;

import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import lombok.RequiredArgsConstructor;
import uk.co.rxmarkets.model.assets.Equity;
import uk.co.rxmarkets.model.markets.EquityMarket;

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
    public Uni<Response> getAllMarkets() {
        return EquityMarket.findAll(client)
                .onItem().transform(Response::ok)
                .onItem().transform(ResponseBuilder::build);
    }

    @GET
    @Path("{mic}")
    public Uni<Response> getSingleMarket(String mic) {
        return Equity.findByMarket(client, mic)
                .onItem().transform(Response::ok)
                .onItem().transform(ResponseBuilder::build);
    }

    @GET
    @Path("{mic}/{ticker}")
    public Uni<Response> getSingleEquity(String mic, String ticker) {
        return Equity.findByTicker(client, mic, ticker)
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
    @Path("{market}/{mic}")
    public Uni<Response> update(Long mic, Equity equity) {
        return equity.update(client)
                .onItem().transform(updated -> updated ? Status.OK : Status.NOT_FOUND)
                .onItem().transform(status -> Response.status(status).build());
    }

    @DELETE
    @Path("{market}/{mic}")
    public Uni<Response> delete(Long mic) {
        // TODO | Useful in testing but should not be available in public API.
        return Equity.delete(client, mic)
                .onItem().transform(deleted -> deleted ? Status.NO_CONTENT : Status.NOT_FOUND)
                .onItem().transform(status -> Response.status(status).build());
    }

}

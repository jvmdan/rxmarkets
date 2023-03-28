package uk.co.rxmarkets.api.resources;

import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import lombok.RequiredArgsConstructor;
import uk.co.rxmarkets.model.assets.Equity;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

@Path("api/equities")
@RequiredArgsConstructor
public class EquityResource {

    private final PgPool client;

    @GET
    @Path("/{market}")
    public Uni<Response> getMarket(String market) {
        return Equity.findByMarket(client, market)
                .onItem().transform(m -> m != null ? Response.ok(m) : Response.status(Status.NOT_FOUND))
                .onItem().transform(ResponseBuilder::build);
    }

    @GET
    @Path("/{market}/{ticker}")
    public Uni<Response> getSingle(String market, String ticker) {
        return Equity.findByTicker(client, market, ticker)
                .onItem().transform(equity -> equity != null ? Response.ok(equity) : Response.status(Status.NOT_FOUND))
                .onItem().transform(ResponseBuilder::build);
    }

}

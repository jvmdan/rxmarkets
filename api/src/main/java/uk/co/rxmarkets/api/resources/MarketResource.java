package uk.co.rxmarkets.api.resources;

import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import lombok.RequiredArgsConstructor;
import uk.co.rxmarkets.model.markets.EquityMarket;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("api/markets")
@RequiredArgsConstructor
public class MarketResource {

    private final PgPool client;

    @GET
    public Uni<Response> getAll() {
        return EquityMarket.findAll(client)
                .onItem().transform(Response::ok)
                .onItem().transform(Response.ResponseBuilder::build);
    }

    @GET
    @Path("/{type}")
    public Uni<Response> getByType(String type) {
        // TODO | For now, we only have one type, so we just return all equity markets.
        return EquityMarket.findAll(client)
                .onItem().transform(response -> type.equalsIgnoreCase("equities")
                        ? Response.ok(response) : Response.status(Response.Status.NOT_FOUND))
                .onItem().transform(Response.ResponseBuilder::build);
    }

}

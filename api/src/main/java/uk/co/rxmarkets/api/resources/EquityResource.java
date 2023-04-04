package uk.co.rxmarkets.api.resources;

import io.smallrye.mutiny.Uni;
import lombok.RequiredArgsConstructor;
import uk.co.rxmarkets.api.model.assets.Equity;
import uk.co.rxmarkets.api.model.markets.EquityMarket;
import uk.co.rxmarkets.api.model.scoring.Scoreboard;
import uk.co.rxmarkets.api.services.equities.EquityService;
import uk.co.rxmarkets.api.services.equities.EquityMarketService;
import uk.co.rxmarkets.api.services.ScoreboardService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.List;

@Path("api/equities")
@RequiredArgsConstructor
@Produces("application/json")
public class EquityResource {

    private final EquityService equityService;
    private final EquityMarketService marketService;
    private final ScoreboardService scoreboardService;

    @GET
    public Uni<List<EquityMarket>> getSupportedMarkets() {
        return marketService.findEquityMarkets();
    }

    @GET
    @Path("/{market}")
    public Uni<List<Equity>> getMarket(String market) {
        return equityService.findMarket(market);
    }

    @GET
    @Path("/{market}/{ticker}")
    public Uni<List<Scoreboard>> getScores(String market, String ticker) {
        return equityService.findSingleEquity(market, ticker)
                .flatMap(scoreboardService::findByEquity);
    }

//    @DELETE
//    @Path("{id}")
//    public Uni<Response> delete(Integer id) {
//        return sf.withTransaction((s,t) -> s.find(Equity.class, id)
//                        .onItem().ifNull().failWith(new WebApplicationException("Equity missing from database.", NOT_FOUND))
//                        .call(s::remove))
//                .replaceWith(Response.ok().status(NO_CONTENT)::build);
//    }

}

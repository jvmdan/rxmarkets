package uk.co.rxmarkets.api.resources;

import io.smallrye.mutiny.Uni;
import lombok.RequiredArgsConstructor;
import uk.co.rxmarkets.api.model.assets.Equity;
import uk.co.rxmarkets.api.model.markets.EquityMarket;
import uk.co.rxmarkets.api.model.scoring.Scoreboard;
import uk.co.rxmarkets.api.services.repo.EquityMarketRepository;
import uk.co.rxmarkets.api.services.repo.EquityRepository;
import uk.co.rxmarkets.api.services.repo.ScoreboardRepository;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("api/equities")
@RequiredArgsConstructor
@Produces("application/json")
public class EquityResource {

    private final EquityRepository equityService;
    private final EquityMarketRepository marketService;
    private final ScoreboardRepository scoreboardService;

    @GET
    public Uni<List<EquityMarket>> getSupportedMarkets() {
        return marketService.findAll();
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

    @GET
    @Path("/{market}/{ticker}/{score}")
    public Uni<Scoreboard> getSingleScore(String market, String ticker, String score) {
        return scoreboardService.findById(score);
    }

    @DELETE
    @Path("/{market}/{ticker}/{score}")
    public Uni<Response> deleteSingleScore(String market, String ticker, String score) {
        return scoreboardService.delete(score);
    }

}

package uk.co.rxmarkets.api.events;

import io.quarkus.vertx.ConsumeEvent;
import io.vertx.mutiny.core.Vertx;
import io.vertx.mutiny.core.eventbus.EventBus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import uk.co.rxmarkets.api.services.EngineService;
import uk.co.rxmarkets.model.EngineRequest;
import uk.co.rxmarkets.model.assets.Equity;
import uk.co.rxmarkets.model.scoring.Scoreboard;

@RequiredArgsConstructor
@Slf4j
public class EngineEvents {

    private final EventBus bus;
    private final EngineService engineService;

    @ConsumeEvent("evaluate")
    public Scoreboard evaluate(EngineRequest request) {
        // Evaluate the request & generate a corresponding scoreboard.
        log.info("Evaluating sentiment for {}:{}...", request.getMarket(), request.getTicker());
        final Scoreboard result = engineService.evaluate(request.getDataSet());
        final Equity update = new Equity(123L, request.getMarket(), request.getTicker(), true, result);
        bus.requestAndForget("persist", update);
        return result;
    }

}

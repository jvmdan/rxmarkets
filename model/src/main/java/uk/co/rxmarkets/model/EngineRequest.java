package uk.co.rxmarkets.model;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import uk.co.rxmarkets.model.ranking.Ranked;

import java.util.Set;
import java.util.UUID;

@RequiredArgsConstructor
@Data
@RegisterForReflection
public class EngineRequest {

    private final UUID id;
    private String market;
    private String ticker;
    private Set<Ranked> dataSet;

    public EngineRequest() {
        this.id = UUID.randomUUID();
    }

    public EngineRequest(String market, String ticker, Set<Ranked> dataSet) {
        this.id = UUID.randomUUID();
        this.market = market;
        this.ticker = ticker;
        this.dataSet = dataSet;
    }

}

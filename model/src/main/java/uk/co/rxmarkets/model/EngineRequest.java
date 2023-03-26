package uk.co.rxmarkets.model;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import uk.co.rxmarkets.model.ranking.Opinion;
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
    private Set<Opinion> dataSet;

    public EngineRequest() {
        this.id = UUID.randomUUID();
    }

    public EngineRequest(String market, String ticker, Set<Opinion> dataSet) {
        this.id = UUID.randomUUID();
        this.market = market;
        this.ticker = ticker;
        this.dataSet = dataSet;
    }

    @Override
    public String toString() {
        return "EngineRequest{" +
                "id=" + id +
                ", market='" + market + '\'' +
                ", ticker='" + ticker + '\'' +
                '}';
    }

}

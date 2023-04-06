package uk.co.rxmarkets.engine.requests;

import lombok.Data;
import uk.co.rxmarkets.engine.model.Opinion;

import java.time.Instant;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Data
public class EngineRequest {

    private UUID id;
    private String market;
    private String ticker;
    private Set<Opinion> dataSet;
    private Date date;

    public EngineRequest() {
        // No-args constructor is required for deserialisation of EngineRequest object.
    }

    public EngineRequest(String market, String ticker, Set<Opinion> dataSet) {
        this(market, ticker, dataSet, Date.from(Instant.now()));
    }

    public EngineRequest(String market, String ticker, Set<Opinion> dataSet, Date date) {
        this.id = UUID.randomUUID();
        this.market = market;
        this.ticker = ticker;
        this.dataSet = dataSet;
        this.date = date;
    }

    @Override
    public String toString() {
        // Ignores 'dataSet' field since it is not required in our String representation.
        return "EngineRequest{" +
                "id=" + id +
                ", market='" + market + '\'' +
                ", ticker='" + ticker + '\'' +
                ", date=" + date +
                '}';
    }

}

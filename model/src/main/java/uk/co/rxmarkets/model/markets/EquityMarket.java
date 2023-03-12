package uk.co.rxmarkets.model.markets;

import lombok.Data;
import uk.co.rxmarkets.model.assets.Equity;

@Data
public class EquityMarket implements Market<Equity> {

    private final Long id;
    private final String name;
    private final String mic;

    @Override
    public Class<Equity> getType() {
        return Equity.class;
    }

}

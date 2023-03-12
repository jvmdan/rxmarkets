package uk.co.rxmarkets.model.assets;

import lombok.Data;

@Data
public class Commodity implements Asset {

    private final Long id;
    private final String ticker;
    private boolean active;

    @Override
    public String getTicker() {
        return ""; // TODO | Commodities do not have a ticker in the same way as other asset classes.
    }

    @Override
    public boolean isActive() {
        return true; // Commodities are always actively traded.
    }

}

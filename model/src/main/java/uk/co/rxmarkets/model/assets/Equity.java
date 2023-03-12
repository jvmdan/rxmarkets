package uk.co.rxmarkets.model.assets;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Equity implements Asset {

    private final String name;
    private final String ticker;

    private boolean active;

    @Override
    public String id() {
        return this.ticker;
    }

    @Override
    public boolean isActivelyTraded() {
        return this.active;
    }

}

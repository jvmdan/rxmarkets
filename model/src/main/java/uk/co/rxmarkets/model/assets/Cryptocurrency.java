package uk.co.rxmarkets.model.assets;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Cryptocurrency implements Asset {

    private final String ticker;
    private final boolean active;

    @Override
    public String id() {
        return this.ticker;
    }

    @Override
    public boolean isActivelyTraded() {
        return active;
    }

}

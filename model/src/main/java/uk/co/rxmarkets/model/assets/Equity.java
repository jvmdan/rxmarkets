package uk.co.rxmarkets.model.assets;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Equity implements Asset {

    private final String name;
    private final String ticker;

    @Override
    public String getId() {
        return this.ticker;
    }
}

package uk.co.rxmarkets.model.assets;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Cryptocurrency implements Asset {

    private final String ticker;

    @Override
    public String getId() {
        return this.ticker;
    }

}

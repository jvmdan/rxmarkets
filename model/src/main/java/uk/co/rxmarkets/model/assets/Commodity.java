package uk.co.rxmarkets.model.assets;

import lombok.Getter;

/**
 * @param id TODO | How do we identify commodities?
 */
@Getter
public record Commodity(String id) implements Asset {

    @Override
    public boolean isActivelyTraded() {
        return true; // Commodities are always actively traded.
    }

}

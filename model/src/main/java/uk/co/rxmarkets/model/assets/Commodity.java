package uk.co.rxmarkets.model.assets;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class Commodity implements Asset {

    private final String id; // TODO | How do we identify commodities?

}

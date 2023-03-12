package uk.co.rxmarkets.model.assets;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class Cryptocurrency implements Asset {

    private final Long id;
    private final String ticker;
    private final boolean active;


}

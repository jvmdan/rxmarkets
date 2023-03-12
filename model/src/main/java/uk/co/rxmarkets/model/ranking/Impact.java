package uk.co.rxmarkets.model.ranking;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Impact {

    STRONGLY_POSITIVE(2),
    POSITIVE(1),
    NEUTRAL(0),
    NEGATIVE(-1),
    STRONGLY_NEGATIVE(-2);

    private final int weight;

}

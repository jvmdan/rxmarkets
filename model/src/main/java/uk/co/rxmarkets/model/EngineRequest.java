package uk.co.rxmarkets.model;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import uk.co.rxmarkets.model.ranking.Ranked;

import java.util.Set;

@RequiredArgsConstructor
@Getter
@RegisterForReflection
public class EngineRequest {

    private final String market;
    private final String ticker;
    private final Set<Ranked> dataSet;

}

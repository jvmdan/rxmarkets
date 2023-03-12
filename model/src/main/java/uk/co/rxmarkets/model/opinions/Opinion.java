package uk.co.rxmarkets.model.opinions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import uk.co.rxmarkets.model.Source;

@RequiredArgsConstructor
@Getter
public class Opinion {

    private final Source source;
    private final String data;
    private final Classification classification;

}

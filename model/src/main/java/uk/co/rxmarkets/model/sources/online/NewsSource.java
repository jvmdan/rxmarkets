package uk.co.rxmarkets.model.sources.online;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import uk.co.rxmarkets.model.sources.OnlineSource;

import java.net.URL;

@RequiredArgsConstructor
@Getter
public class NewsSource extends OnlineSource {

    private final URL url;

}

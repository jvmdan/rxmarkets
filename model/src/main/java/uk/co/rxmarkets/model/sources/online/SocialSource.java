package uk.co.rxmarkets.model.sources.online;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import uk.co.rxmarkets.model.ranking.Ranked;
import uk.co.rxmarkets.model.sources.OnlineSource;

import java.net.URL;
import java.util.Set;

@RequiredArgsConstructor
@Getter
public class SocialSource extends OnlineSource {

    private final URL url;

    @Override
    public Set<Ranked> getRankedSet() {
        return null;
    }

}

package uk.co.rxmarkets.model.sources.offline;

import lombok.RequiredArgsConstructor;
import uk.co.rxmarkets.model.ranking.Ranked;
import uk.co.rxmarkets.model.sources.OfflineSource;

import java.io.File;
import java.net.URI;
import java.util.Set;

/**
 * The JsonSource class is an example of an OfflineSource that expects a JSON file containing
 * the data we wish to process.
 */
@RequiredArgsConstructor
public class JsonSource extends OfflineSource {

    private final File file;

    @Override
    public Set<Ranked> getRankedSet() {
        return null;
    }

    @Override
    protected URI getUri() {
        return file.toURI();
    }

}

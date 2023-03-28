package uk.co.rxmarkets.api.resources;

import io.smallrye.mutiny.Uni;
import io.vertx.core.Future;
import io.vertx.core.json.JsonArray;
import lombok.RequiredArgsConstructor;
import org.jboss.resteasy.reactive.RestQuery;
import uk.co.rxmarkets.api.data.reddit.RedditScraper;
import uk.co.rxmarkets.model.ranking.Opinion;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("api/data")
@RequiredArgsConstructor
public class DataResource {

    private static final int DEFAULT_DATA_SIZE = 50;

    private final RedditScraper redditScraper;

    @GET
    @Path("/reddit")
    public Uni<JsonArray> get(@RestQuery String subreddit, @RestQuery String count) {
        if (subreddit.isBlank()) return null;
        return redditScraper.scrape(subreddit, count != null ? Integer.parseInt(count) : DEFAULT_DATA_SIZE);
    }

}

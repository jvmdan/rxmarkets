package uk.co.rxmarkets.api.data;

import io.smallrye.mutiny.Uni;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.mutiny.core.eventbus.EventBus;
import io.vertx.mutiny.core.eventbus.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jboss.resteasy.reactive.RestQuery;
import uk.co.rxmarkets.api.EngineService;
import uk.co.rxmarkets.model.assets.Equity;
import uk.co.rxmarkets.model.ranking.Opinion;
import uk.co.rxmarkets.model.ranking.Ranked;
import uk.co.rxmarkets.model.scoring.Scoreboard;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

@RequiredArgsConstructor
@Path("api/static")
@Slf4j
public class StaticDataResource {

    private final EventBus bus;
    private final EngineService engineService;

    /**
     * A public-facing method that allows us to trigger the reading of static data, and
     * produces a Scoreboard instance based upon the data within.
     *
     * @return the Scoreboard generated by the EngineService on our data-set.
     */
    @GET
    @Path("/trigger")
    public Uni<Scoreboard> trigger(@RestQuery String equity) {
        final String exampleFile = equity + "-tweets.json";
        return bus.<JsonArray>request("tweets", exampleFile)
                .onItem().transform(message -> {
                    // Pull the dataset out from the static file of tweets.
                    final Set<Ranked> dataSet = new HashSet<>(message.body().size());
                    message.body().stream()
                            .filter(JsonObject.class::isInstance)
                            .map(JsonObject.class::cast)
                            .forEach(e -> {
                                final String source = e.getString("author");
                                final String data = e.getString("data");
                                dataSet.add(new Opinion(source, data));
                            });

                    // Trigger the engine to evaluate the dataset in all categories.
                    final Scoreboard result = engineService.evaluate(dataSet);

                    // Fire & forget the "persist" event with an updated Equity object.
                    final String ticker = equity.toUpperCase(Locale.ROOT);
                    final Equity update = new Equity(123L, "XLON", ticker, true, result);
                    bus.requestAndForget("persist", update);

                    return result;
                });
    }

    /**
     * This method allows you to view the raw static data via the API, providing you know the filename.
     *
     * @param file the name of the file you wish to read the JSON content of.
     * @return a JsonArray containing all JsonObjects within the given file.
     */
    @GET
    @Path("/raw")
    public Uni<JsonArray> rawData(@RestQuery String file) {
        return bus.<JsonArray>request("tweets", file)
                .onItem().transform(Message::body);
    }

}

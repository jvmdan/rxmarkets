package uk.co.rxmarkets.engine.auto;

import io.quarkus.runtime.StartupEvent;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import uk.co.rxmarkets.engine.model.Opinion;
import uk.co.rxmarkets.engine.requests.EngineRequest;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Stream;

@ApplicationScoped
@Slf4j
public class StartupTask {

    private final Emitter<EngineRequest> emitter;
    private final boolean enabled;
    private final String baseDir;
    private final Vertx vertx;

    public StartupTask(@Channel("engine-requests") Emitter<EngineRequest> emitter,
                       @ConfigProperty(name = "engine.auto.startup.enabled", defaultValue = "true") boolean enabled,
                       @ConfigProperty(name = "engine.auto.startup.base-dir", defaultValue = "static/") String baseDir) {
        this.emitter = emitter;
        this.enabled = enabled;
        this.baseDir = baseDir;
        this.vertx = Vertx.vertx();
    }

    void onStart(@Observes StartupEvent ev) throws IOException {
        if (enabled) {
            // Read all JSON files in the 'static' resources folder and process into EngineRequests.
            final URL baseUrl = getClass().getClassLoader().getResource(baseDir);
            if (baseUrl == null || baseUrl.getPath() == null) {
                throw new FileNotFoundException(baseDir);
            } else try (Stream<Path> stream = Files.walk(Path.of(baseUrl.getPath()))) {
                stream.filter(Files::isRegularFile)
                        .map(this::unpackJson)
                        .forEach(dataSet -> {
                            // We have extracted our dataset, so we propagate downstream to be processed by the engine.
                            final String[] parameters = splitPath(dataSet.iterator().next().getSource());
                            final String market = parameters[1].toUpperCase(Locale.ROOT);
                            final String ticker = parameters[2].toUpperCase(Locale.ROOT);
                            final String date = parameters[3].substring(parameters[3].lastIndexOf("_") + 1, parameters[3].lastIndexOf(".json"));
                            final EngineRequest request = new EngineRequest(market, ticker, dataSet, parseDate(date));
                            emitter.send(request);
                            log.info("Sent request for processing: {}", request.getId());
                        });
            }
        }
    }

    private Set<Opinion> unpackJson(Path path) {
        final Set<Opinion> results = new HashSet<>();
        final Buffer jsonData = vertx.fileSystem().readFileBlocking(path.toString());
        final JsonArray jsonArray = new JsonArray(jsonData);
        log.info("Extracted {} data points from file @ \"{}\"", jsonArray.size(), path);
        jsonArray.stream()
                .filter(JsonObject.class::isInstance)
                .map(JsonObject.class::cast)
                .forEachOrdered(jsonObj -> {
                    final String content = jsonObj.getString("data");
                    final Opinion data = new Opinion(path.toString(), content);
                    results.add(data);
                });
        return results;
    }

    private static String[] splitPath(String filePath) {
        return filePath.substring(filePath.lastIndexOf("static")).split("/");
    }

    private static Date parseDate(String dateString) {
        final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return dateFormat.parse(dateString);
        } catch (ParseException e) {
            return Date.from(Instant.now());
        }
    }

}
package uk.co.rxmarkets.engine.auto;

import io.quarkus.runtime.StartupEvent;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import uk.co.rxmarkets.engine.requests.EngineRequest;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

@ApplicationScoped
@Slf4j
public class StartupTask {

    private final Emitter<EngineRequest> emitter;
    private final boolean enabled;
    private final String baseDir;

    public StartupTask(@Channel("engine-requests") Emitter<EngineRequest> emitter,
                       @ConfigProperty(name = "engine.auto.startup.enabled", defaultValue = "true") boolean enabled,
                       @ConfigProperty(name = "engine.auto.startup.base-dir", defaultValue = "static/") String baseDir) {
        this.emitter = emitter;
        this.enabled = enabled;
        this.baseDir = baseDir;
    }

    void onStart(@Observes StartupEvent ev) throws IOException {
        if (enabled) {
            // Read all JSON files in the 'static' resources folder, and process into EngineRequests.
            final URL baseUrl = getClass().getClassLoader().getResource(baseDir);
            if (baseUrl == null || baseUrl.getPath() == null) {
                throw new FileNotFoundException(baseDir);
            } else try (Stream<Path> stream = Files.walk(Path.of(baseUrl.getPath()))) {
                stream.filter(Files::isRegularFile)
                        .forEach(path -> log.info("Found file @ \"{}\"", path));
            }
        }
    }

}

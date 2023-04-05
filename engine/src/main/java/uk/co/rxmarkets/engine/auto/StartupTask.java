package uk.co.rxmarkets.engine.auto;

import io.quarkus.runtime.StartupEvent;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import uk.co.rxmarkets.engine.requests.EngineRequest;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;

@ApplicationScoped
@Slf4j
public class StartupTask {

    private final boolean enabled;
    private final Emitter<EngineRequest> emitter;

    public StartupTask(@Channel("engine-requests") Emitter<EngineRequest> emitter,
                       @ConfigProperty(name = "engine.auto.startup.enabled", defaultValue = "true") boolean enabled) {
        this.emitter = emitter;
        this.enabled = enabled;
    }

    void onStart(@Observes StartupEvent ev) {
        if (enabled) {
            // Read all JSON files in the 'static' resources folder, and process into EngineRequests.
            log.info("Found a total of 0 files to process");
        }
    }

}

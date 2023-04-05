package uk.co.rxmarkets.engine.auto;

import io.quarkus.scheduler.Scheduled;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import uk.co.rxmarkets.engine.requests.EngineRequest;

import javax.enterprise.context.ApplicationScoped;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicInteger;

@ApplicationScoped
@Slf4j
public class ScheduledTask {

    private final AtomicInteger counter = new AtomicInteger();
    private final Emitter<EngineRequest> emitter;
    private final boolean enabled;

    public ScheduledTask(@Channel("engine-requests") Emitter<EngineRequest> emitter,
                         @ConfigProperty(name = "engine.auto.scheduled.enabled") boolean enabled) {
        this.emitter = emitter;
        this.enabled = enabled;
    }

    @Scheduled(cron = "{engine.auto.scheduled.cron}")
    void scheduledTask() {
        // TODO | Gather a data set from all available sources & propagate downstream.
        if (enabled) {
            final int count = counter.incrementAndGet();
            log.info("Scheduled job executed! [n={}]", count);
            final EngineRequest request = new EngineRequest("XNAS", "MSFT", Collections.emptySet());
            emitter.send(request);
        }
    }

}
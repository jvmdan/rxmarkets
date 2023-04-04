package uk.co.rxmarkets.gatherer;

import java.util.Collections;
import java.util.concurrent.atomic.AtomicInteger;
import javax.enterprise.context.ApplicationScoped;
import io.quarkus.scheduler.Scheduled;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import uk.co.rxmarkets.api.model.EngineRequest;

@ApplicationScoped
@Slf4j
public class ScheduledBean {

    private final AtomicInteger counter = new AtomicInteger();

    @Channel("engine-requests")
    Emitter<EngineRequest> emitter;

    public int get() {
        return counter.get();
    }

    @Scheduled(cron = "{cron.expr}")
    void scheduledTask() {
        // TODO | Gather a data set from all available sources & propagate downstream.
        final int count = counter.incrementAndGet();
        log.info("Scheduled job executed! [n={}]", count);
        final EngineRequest request = new EngineRequest("XLON", "CS", Collections.emptySet());
        emitter.send(request);
    }

}
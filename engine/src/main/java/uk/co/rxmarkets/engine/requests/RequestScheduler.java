package uk.co.rxmarkets.engine.requests;

import io.quarkus.scheduler.Scheduled;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

import javax.enterprise.context.ApplicationScoped;
import java.util.Collections;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicInteger;

@ApplicationScoped
@Slf4j
public class RequestScheduler {

    @Channel("engine-requests")
    Emitter<EngineRequest> emitter;

    private final AtomicInteger counter = new AtomicInteger();

    public int get() {
        return counter.get();
    }

    @Scheduled(cron = "{cron.expr}")
    void scheduledTask() {
        // TODO | Gather a data set from all available sources & propagate downstream.
        final int count = counter.incrementAndGet();
        log.info("Scheduled job executed! [n={}]", count);
        final EngineRequest request = new EngineRequest("XNAS", "MSFT", Collections.emptySet());
        emitter.send(request);
    }

}
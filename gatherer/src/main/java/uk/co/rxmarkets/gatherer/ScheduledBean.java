package uk.co.rxmarkets.gatherer;

import java.util.concurrent.atomic.AtomicInteger;
import javax.enterprise.context.ApplicationScoped;
import io.quarkus.scheduler.Scheduled;

@ApplicationScoped
public class ScheduledBean {

    private final AtomicInteger counter = new AtomicInteger();

    public int get() {
        return counter.get();
    }

    @Scheduled(cron = "{cron.expr}")
    void scheduledTask() {
        counter.incrementAndGet();
    }

}
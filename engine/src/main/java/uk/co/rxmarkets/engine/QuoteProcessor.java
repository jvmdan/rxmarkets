package uk.co.rxmarkets.engine;

import io.smallrye.reactive.messaging.annotations.Blocking;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Outgoing;
import uk.co.rxmarkets.model.Quote;

import javax.enterprise.context.ApplicationScoped;
import java.util.Random;

/**
 * A bean consuming data from the "request" RabbitMQ queue and giving out a random quote.
 * The result is pushed to the "quotes" RabbitMQ exchange.
 */
@ApplicationScoped
public class QuoteProcessor {

    private Random random = new Random();

    @Incoming("requests")
    @Outgoing("quotes")
    @Blocking
    public Quote process(String quoteRequest) throws InterruptedException {
        Thread.sleep(200); // Simulate some hard working task
        return new Quote(quoteRequest, random.nextInt(100));
    }
}

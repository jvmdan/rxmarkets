package uk.co.rxmarkets.view;

import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateExtension;
import io.quarkus.qute.TemplateInstance;
import org.jboss.resteasy.reactive.RestQuery;
import uk.co.rxmarkets.model.scoring.Indicator;
import uk.co.rxmarkets.model.scoring.Scoreboard;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Path("/view")
public class EquityResource {

    /**
     * The number of days worth of data we wish to plot.
     */
    private static final int N_DAYS = 28;

    @CheckedTemplate
    static class Templates {

        static native TemplateInstance view(String market, String ticker, List<Scoreboard> scores);

    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance get(@RestQuery String market, @RestQuery String ticker) {
        final long dayInMillis = 1000 * 60 * 60 * 24;
        final Date today = Date.from(Instant.now());
        final List<Scoreboard> scores = new ArrayList<>();
        for (int i = 0; i < N_DAYS; i++) {
            final Date date = new Date(today.getTime() - (i * dayInMillis));
            final Scoreboard result = Scoreboard.random(date);
            scores.add(result); // TODO | Extract from API.
        }
        return EquityResource.Templates.view(market, ticker, scores);
    }

    /**
     * This template extension method implements the "halveScore" computed property.
     * This method exists to demonstrate that you can call functions in Java from
     * within the HTML template. Simply call '.halveScore' on your given object.
     */
    @TemplateExtension
    static BigDecimal halveScore(Indicator i) {
        return BigDecimal.valueOf(i.score()).multiply(new BigDecimal("0.5"));
    }

}

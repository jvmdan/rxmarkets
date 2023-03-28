package uk.co.rxmarkets.view;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateExtension;
import io.quarkus.qute.TemplateInstance;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.jboss.resteasy.reactive.RestQuery;
import uk.co.rxmarkets.model.scoring.Indicator;
import uk.co.rxmarkets.model.scoring.Scoreboard;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.math.BigDecimal;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

/**
 * The EquityResource serves the dynamic 'view' template for a given equity.
 *
 * @author Daniel Scarfe
 */
@Path("/view")
@RequiredArgsConstructor
public class EquityResource {

    private final EnvironmentConfiguration config;
    private final ObjectMapper mapper;

    @CheckedTemplate
    static class Templates {

        static native TemplateInstance view(String market, String ticker, List<Scoreboard> scores);

    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    @SneakyThrows
    public TemplateInstance get(@RestQuery String market, @RestQuery String ticker) {
        final URL source = new URL(config.getBaseUrl() + "/scores/random");
        final Scoreboard[] scores = mapper.readValue(source, Scoreboard[].class);
        return EquityResource.Templates.view(market, ticker, Arrays.asList(scores));
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

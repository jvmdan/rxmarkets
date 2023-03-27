package uk.co.rxmarkets.view;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.reactive.RestQuery;

import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;

@Path("/")
public class IndexResource {

    @Inject
    Template index;

    @GET
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance get(@RestQuery String name) {
        return index.data("name", name);
    }

}

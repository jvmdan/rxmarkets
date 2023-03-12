package uk.co.rxmarkets.api.examples;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("api/static")
public class VertxJsonResource {

    @GET
    @Path("{name}/object")
    public JsonObject jsonObject(String name) {
        return new JsonObject().put("Hello", name);
    }

    @GET
    @Path("{name}/array")
    public JsonArray jsonArray(String name) {
        return new JsonArray().add("Hello").add(name);
    }
}

package uk.co.rxmarkets.engine;

import io.vertx.core.Vertx;
import uk.co.rxmarkets.engine.controller.WebVerticle;

public class Engine {

    public static void main(String[] args) {
        final Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(WebVerticle.class.getName());
    }

}

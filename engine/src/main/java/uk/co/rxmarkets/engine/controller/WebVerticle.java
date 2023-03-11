package uk.co.rxmarkets.engine.controller;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WebVerticle extends AbstractVerticle implements Controller {

    @Override
    public void start(Promise<Void> startPromise) throws Exception {
        vertx.createHttpServer()
                .requestHandler(r -> {
                    log.info("Received request from client");
                    r.response().end("Welcome to Vert.x Intro");
                })
                .listen(config().getInteger("server.port", 8888),
                        result -> {
                            if (result.succeeded()) {
                                log.info("Started HTTP server");
                                startPromise.complete();
                            } else {
                                startPromise.fail(result.cause());
                            }
                        });
    }

    @Override
    public void stop() {
        log.info("Shutting down application");
    }

}

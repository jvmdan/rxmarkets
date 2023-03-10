package uk.co.rxmarkets.engine.controller;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WebVerticle extends AbstractVerticle implements Controller {

    @Override
    public void start(Promise<Void> startPromise) throws Exception {
        vertx.createHttpServer().requestHandler(req -> {
            log.info("Received request");
            req.response()
                    .putHeader("content-type", "text/plain")
                    .end("Hello from Vert.x!");
        }).listen(8888, http -> {
            if (http.succeeded()) {
                startPromise.complete();
                log.info("Started Web Service on port 8888");
            } else {
                startPromise.fail(http.cause());
            }
        });
    }

    @Override
    public void stop() {
        log.info("Shutting down application");
    }

}

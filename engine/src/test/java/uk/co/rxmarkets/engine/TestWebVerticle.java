package uk.co.rxmarkets.engine;

import io.vertx.core.Vertx;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.RunTestOnContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import uk.co.rxmarkets.engine.controller.WebVerticle;

@RunWith(VertxUnitRunner.class)
public class TestWebVerticle {

    @Rule
    public RunTestOnContext rule = new RunTestOnContext();

    @Before
    public void deploy_verticle(TestContext testContext) {
        Vertx vertx = rule.vertx();
        vertx.deployVerticle(new WebVerticle(), testContext.asyncAssertSuccess());
    }

    @Test
    public void verticle_deployed(TestContext testContext) throws Throwable {
        Async async = testContext.async();
        async.complete();
    }

}

package com.vala.demo;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;

import java.time.LocalDateTime;

public class MainVerticle extends AbstractVerticle {

    private Repository repository = new Repository();

    @Override
    public void start() {
        Router router = Router.router(vertx);

        router.post("/api/click/new").handler(ctx -> {
            repository.incrementCount();
            ctx.response().end(LocalDateTime.now().toString());
        });

        router.get("/api/clicks/get-count").handler(ctx -> ctx.response()
            .putHeader("Content-Type", "application/json")
            .putHeader("Access-Control-Allow-Origin", "*")
            .end(new JsonObject().put("clickCount", repository.getCount()).encode()));

        vertx.createHttpServer().requestHandler(router::accept).listen(8080);
    }

}

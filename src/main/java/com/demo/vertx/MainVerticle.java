package com.demo.vertx;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.bridge.BridgeEventType;
import io.vertx.ext.bridge.PermittedOptions;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.sockjs.BridgeOptions;
import io.vertx.ext.web.handler.sockjs.SockJSHandler;

import java.time.LocalDateTime;
import java.util.function.Function;

public class MainVerticle extends AbstractVerticle {

    private static final String FEED_ADDRESS = "counter-feed";

    private Repository repository = new Repository();

    @Override
    public void start() {
        Router router = Router.router(vertx);

        BridgeOptions options =
            new BridgeOptions().addOutboundPermitted(
                new PermittedOptions().setAddress(FEED_ADDRESS));

        router.route("/eventbus/*").handler(
            SockJSHandler.create(vertx).bridge(options, event -> {
                if (event.type() == BridgeEventType.SOCKET_CREATED) {
                    System.out.println("A socket was created");
                }
                event.complete(true);
            }));

        Function<Integer, String> toJson =
            i -> new JsonObject().put("clickCount", i).encode();

        router.get("/api/clicks").handler(ctx -> ctx.response()
            .putHeader("Content-Type", "application/json")
            .putHeader("Access-Control-Allow-Origin", "*")
            .end(toJson.apply(repository.getCount())));

        router.post("/api/clicks").handler(context -> {
            vertx.<Integer>executeBlocking(future -> {
                int newCount = repository.incrementCount();
                future.complete(newCount);
            }, res -> {
                if (res.succeeded()) {
                    vertx.eventBus().publish(
                        FEED_ADDRESS, toJson.apply(res.result()));
                }
            });

            context.response().end(LocalDateTime.now().toString());
        });

        vertx.createHttpServer().requestHandler(router::accept).listen(8080);
    }

}

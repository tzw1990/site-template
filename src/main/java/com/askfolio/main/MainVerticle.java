package com.askfolio.main;

import com.askfolio.user.User;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServer;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClient;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.StaticHandler;

import java.util.List;
import java.util.stream.Collectors;

public class MainVerticle extends AbstractVerticle {

    public static final String COLLECTION = "user";
    private MongoClient mongo;

    /**
     * This method is called when the verticle is deployed. It creates a HTTP server and registers a simple request
     * handler.
     * <p/>
     * Notice the `listen` method. It passes a lambda checking the port binding result. When the HTTP server has been
     * bound on the port, it call the `complete` method to inform that the starting has completed. Else it reports the
     * error.
     *
     * @param fut the future
     */
    @Override
    public void start(Future<Void> fut) {
        // Create a Mongo client
        mongo = MongoClient.createShared(vertx, config());

        startWebApp((http) -> completeStartup(http, fut));
    }

    private void startWebApp(Handler<AsyncResult<HttpServer>> next) {
        // Create a router object.
        Router router = Router.router(vertx);

        router.get("/api/users").handler(this::getAll);
        router.route("/api/users*").handler(BodyHandler.create());
        router.route("/api/users").method(HttpMethod.POST).handler(this::addOne);
        router.get("/api/users/:id").handler(this::getOne);
        router.put("/api/users/:id").handler(this::updateOne);
        router.delete("/api/users/:id").handler(this::deleteOne);

        router.route().handler(StaticHandler.create("dist"));

        // Create the HTTP server and pass the "accept" method to the request handler.
        vertx
            .createHttpServer()
            .requestHandler(router::accept)
            .listen(
                // Retrieve the port from the configuration,
                // default to 8080.
                config().getInteger("http.port", 8080),
                next
            );
    }

    private void completeStartup(AsyncResult<HttpServer> http, Future<Void> fut) {
        if (http.succeeded()) {
            fut.complete();
        } else {
            fut.fail(http.cause());
        }
    }

    @Override
    public void stop() throws Exception {
        mongo.close();
    }

    private void addOne(RoutingContext routingContext) {
        final User user = Json.decodeValue(routingContext.getBodyAsString(), User.class);

        mongo.insert(COLLECTION, user.toJson(), r -> {
            user.setId(r.result());
            routingContext.response()
                .setStatusCode(201)
                .putHeader("content-type", "application/json; charset=utf-8")
                .end(Json.encodePrettily(user));
        });
    }

    private void getOne(RoutingContext routingContext) {
        final String id = routingContext.request().getParam("id");
        if (id == null) {
            routingContext.response().setStatusCode(400).end();
        } else {
            mongo.findOne(COLLECTION, new JsonObject().put("_id", id), null, ar -> {
                if (ar.succeeded()) {
                    if (ar.result() == null) {
                        routingContext.response().setStatusCode(404).end();
                        return;
                    }
                    User user = new User(ar.result());
                    routingContext.response()
                        .setStatusCode(200)
                        .putHeader("content-type", "application/json; charset=utf-8")
                        .end(Json.encodePrettily(user));
                } else {
                    routingContext.response().setStatusCode(404).end();
                }
            });
        }
    }

    private void updateOne(RoutingContext routingContext) {
        final String id = routingContext.request().getParam("id");
        JsonObject json = routingContext.getBodyAsJson();
        if (id == null || json == null) {
            routingContext.response().setStatusCode(400).end();
        } else {
            mongo.findOneAndUpdate(COLLECTION,
                new JsonObject().put("_id", id), // Select a unique document
                // The update syntax: {$set, the json object containing the fields to update}
                new JsonObject()
                    .put("$set", json),
                v -> {
                    if (v.failed()) {
                        routingContext.response().setStatusCode(404).end();
                    } else {
                        routingContext.response()
                            .putHeader("content-type", "application/json; charset=utf-8")
                            .end(Json.encodePrettily(
                                new User(id,
                                    json.getString("userName"),
                                    json.getString("passWord"),
                                    json.getString("firstName"),
                                    json.getString("lastName"),
                                    json.getString("address")))
                            );
                    }
                });
        }
    }

    private void deleteOne(RoutingContext routingContext) {
        String id = routingContext.request().getParam("id");
        if (id == null) {
            routingContext.response().setStatusCode(400).end();
        } else {
            mongo.removeDocument(COLLECTION, new JsonObject().put("_id", id),
                ar -> routingContext.response().setStatusCode(204).end());
        }
    }

    private void getAll(RoutingContext routingContext) {
        mongo.find(COLLECTION, new JsonObject(), results -> {
            List<JsonObject> objects = results.result();
            List<User> users = objects.stream().map(User::new).collect(Collectors.toList());
            routingContext.response()
                .putHeader("content-type", "application/json; charset=utf-8")
                .end(Json.encodePrettily(users));
        });
    }

}

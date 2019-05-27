package io.vertx.starter;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;

public class MainVerticle extends AbstractVerticle {
	
  private static final Logger LOGGER = LoggerFactory.getLogger(MainVerticle.class);

  @Override
  public void start() {
	  Router router = Router.router(vertx);
		router.route().handler(BodyHandler.create());
		router.get("/").handler(rc -> rc.response().end("Hello vertx"));
		router.post("/api/post-json-object").handler(this::handleJSONDocumentPost);
	    vertx.createHttpServer()
	        .requestHandler(router)
	        .listen(8081); 
  }
  
  private void handleJSONDocumentPost(RoutingContext rc) {
	  HttpServerResponse response = rc.response();
	  JsonObject jsonObject = rc.getBodyAsJson();
	  if(jsonObject != null) {
		  LOGGER.info("JSON recived succesfully!");
		  System.out.println("JSON object: " + jsonObject);
		  response.end(new JsonObject().put("message", "success").encodePrettily());
	  }else {
		  LOGGER.info("Failed while receiving JSON object!");
		  response.end(new JsonObject().put("message", "failed").encodePrettily());
	  }
  }

}

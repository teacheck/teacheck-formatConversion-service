package io.teacheck.verticle;

import io.teacheck.conversor.Alumnos;
import io.teacheck.conversor.Funciones;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.handler.BodyHandler;

public class HttpVerticle extends AbstractVerticle {
	
  private final static String FICHERO_XSD = "files/fichero.xsd";
  private static final Logger LOGGER = LoggerFactory.getLogger(HttpVerticle.class);

  @Override
  public void start() {
	Router router = Router.router(vertx);
	router.route().handler(BodyHandler.create());
	router.get("/").handler(rc -> rc.response().end("hello from vertx"));
	router.post("/api/post-xml-document").handler(this::handleXMLDocumentPost);
    vertx.createHttpServer()
        .requestHandler(router)
        .listen(8080);
  }
  
  private void handleXMLDocumentPost(RoutingContext rc) {
	  HttpServerResponse response = rc.response();
	  String xmlString = rc.getBodyAsString();
	  Alumnos alumnos;
	  if(Funciones.validateXMLSchema(FICHERO_XSD, xmlString)) {
		  LOGGER.info("XML validated successfully");
		  if((alumnos = Funciones.convertirXMLenObjetos(xmlString)) != null) {
			  LOGGER.info("XML parsed to Java object successfully");	
	          LOGGER.info("XML parsed from Java object: " + alumnos);
			  JsonObject obj = Funciones.convertirObjetosJavaEnJSON(alumnos);
			  if(obj != null) {
				  LOGGER.info("Java object to JSON parsed successfully");
				  LOGGER.info("Java object to JSON object: " + obj);
				  enviarDatosAlServicio(obj);
				  response.end("success");
			  }else {
				  LOGGER.error("Error while parsing objects to JSON");
				  response.end(new JsonObject().put("message", "failed").encodePrettily());
			  }
		  } else {
			  LOGGER.error("Error while parsing XML string");
			  response.end(new JsonObject().put("message", "failed").encodePrettily());
		  }
	  } else {
		  LOGGER.error("Failed while validating XML string");
		  response.end(new JsonObject().put("message", "Failed").encodePrettily());
	  }
  }
  
  private void enviarDatosAlServicio(JsonObject obj) {
	  WebClient client = WebClient.create(vertx);
	  client
	  .post(8081, "172.18.128.177", "/api/post-json-object")
	  .sendJsonObject(obj, res -> {
		  if(res.succeeded()) {
			  LOGGER.info("JSON has been sent!");
		  } else {
			  LOGGER.error("Could not send request!: " + res.cause());
		  }
	  });
	 
  }

}

package io.teacheck.verticle;

import io.teacheck.constants.Constants;
import io.teacheck.conversor.Alumnos;
import io.teacheck.conversor.Conversor;
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
	
  private final static String FICHERO_XSD = "files/schema.xsd";
  private static final Logger LOGGER = LoggerFactory.getLogger(HttpVerticle.class);

  @Override
  public void start() {
	Router router = Router.router(vertx);
	router.route().handler(BodyHandler.create());
	router.get("/").handler(rc -> rc.response().end("Teacheck conversor API"));
	router.post("/api/post-xml-document").handler(this::handleXMLDocumentPost);
    vertx.createHttpServer()
        .requestHandler(router)
        .listen(Constants.HTTP_SERVER_PORT);
  }
  
  private void handleXMLDocumentPost(RoutingContext rc) {
	  HttpServerResponse response = rc.response();
	  String xmlString = rc.getBodyAsString();
	  Alumnos alumnos;
	  if(Conversor.validateXMLSchema(FICHERO_XSD, xmlString)) {
		  LOGGER.info("XML validated successfully");
		  if((alumnos = Conversor.convertirXMLenObjetos(xmlString)) != null) {
			  LOGGER.info("XML parsed to Java object successfully");	
			  JsonObject obj = Conversor.convertirObjetosJavaEnJSON(alumnos);
			  if(obj != null) {
				  LOGGER.info("Java object to JSON parsed successfully");
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
		  response.end(new JsonObject().put("message", "Failed to parse XML").encodePrettily());
	  }
  }
  
  private void enviarDatosAlServicio(JsonObject obj) {
	  WebClient client = WebClient.create(vertx);
	  client
	  .post(Constants.DB_SERVICE_PORT, Constants.DB_SERVICE_HOST, "/api/post-json-object")
	  .sendJsonObject(obj, res -> {
		  if(res.succeeded()) {
			  LOGGER.info("JSON has been sent!");
		  } else {
			  LOGGER.error("Could not send request!: " + res.cause());
		  }
	  });
	 
  }

}

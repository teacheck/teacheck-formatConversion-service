package io.teacheck.conversor;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.xml.sax.SAXException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

public class Conversor {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(Conversor.class);
	
	public static boolean validateXMLSchema(String xsdPath, String xmlString){
        
        try {
            SchemaFactory factory = 
                    SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(new File(xsdPath));
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(new StringReader(xmlString)));
        } catch (IOException | SAXException e) {
            LOGGER.error("IOException: " + e.getMessage());
            return false;
        }
        return true;
    }
	
	public static Alumnos convertirXMLenObjetos (String xmlString) {

        JAXBContext jaxbContext;
        Alumnos alumnos = null;
		try {
			jaxbContext = JAXBContext.newInstance(Alumnos.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            alumnos = (Alumnos) unmarshaller.unmarshal(new StringReader(xmlString));
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return alumnos;
	}
	
	public static JsonObject convertirObjetosJavaEnJSON (Alumnos alumnos) {

		ObjectMapper mapper = new ObjectMapper();
		String jsonString = null;
		try {
			jsonString = mapper.writeValueAsString(alumnos);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return new JsonObject(jsonString);
	}

}

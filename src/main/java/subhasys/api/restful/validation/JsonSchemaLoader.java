/**
 * 
 */
package subhasys.api.restful.validation;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonParser;
import com.github.fge.jsonschema.cfg.ValidationConfiguration;
import com.github.fge.jsonschema.core.load.configuration.LoadingConfiguration;
import com.github.fge.jsonschema.main.JsonSchemaFactory;

import me.andrz.jackson.JsonReferenceException;
import me.andrz.jackson.JsonReferenceProcessor;
import me.andrz.jackson.ObjectMapperFactory;

/**
 * @author subhasis
 *
 */
@Component
public class JsonSchemaLoader {
    private static final Logger LOGGER = LoggerFactory.getLogger(JsonSchemaLoader.class);
	private JsonSchemaFactory jsonSchemaFactory;
	private JsonReferenceProcessor jsonRefProcessor;
	private static final String SCHEMA_FILE_PATH =
			String.format("%s%s", "/validation","/customer-profile-schema.json");
	private File jsonSchemaFile = new File(SCHEMA_FILE_PATH);
	
	private void initJsonRefProcessor() {
		LOGGER.debug("initJsonRefProcessor :: Initializing JsonReferenceProcessor");
		jsonRefProcessor = new JsonReferenceProcessor();
		jsonRefProcessor.setStopOnCircular(false); //default true
		jsonRefProcessor.setPreserveRefs(true);
		jsonRefProcessor.setMaxDepth(100);//default 1, for no Max depth set -1
		jsonRefProcessor.setCacheInMemory(true);
		jsonRefProcessor.setMapperFactory(new ObjectMapperFactory() {
			@Override
			public ObjectMapper create(URL url) {
				return new ObjectMapper().configure(JsonParser.Feature.ALLOW_COMMENTS, true);
			}
		});
		LOGGER.debug("initJsonRefProcessor :: Initializing JsonSchemaFactory");
		//Here Create MessageBundle that can be set to ValidationConfiguration
		jsonSchemaFactory = JsonSchemaFactory.newBuilder()
				.setLoadingConfiguration(LoadingConfiguration.newBuilder().freeze())
				.setValidationConfiguration(ValidationConfiguration.newBuilder().freeze()).freeze();
	}
	
	/**
	 * @return the jsonSchemaFactory
	 */
	public JsonSchemaFactory getJsonSchemaFactory() {
		return jsonSchemaFactory;
	}

	public JsonNode getRequestValidationSchema() throws IOException, JsonReferenceException {
		if (null == jsonRefProcessor) {
			initJsonRefProcessor();
		}
		LOGGER.debug("getRequestValidationSchema :: Loading Nested Schema File >>" 
				+ SCHEMA_FILE_PATH);
		
		PathMatchingResourcePatternResolver resourceResolver = new PathMatchingResourcePatternResolver();
		Resource[] schemaResourceArray = resourceResolver.getResources("classpath:" + SCHEMA_FILE_PATH);
		for (Resource resource : schemaResourceArray) {
			jsonSchemaFile = resource.getFile();
		}
		
		if(jsonSchemaFile == null) {
			throw new IOException("Couldn't load file " + SCHEMA_FILE_PATH + " from Classpath");
		}
		return jsonRefProcessor.process(jsonSchemaFile);
		
	}
	
}

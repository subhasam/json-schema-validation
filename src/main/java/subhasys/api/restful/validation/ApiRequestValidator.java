/**
 * 
 */
package subhasys.api.restful.validation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jackson.JsonLoader;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.core.report.ProcessingMessage;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.main.JsonSchema;
import me.andrz.jackson.JsonReferenceException;

/**
 * @author subhasis
 *
 */
@Component
public class ApiRequestValidator {

	private static final Logger LOGGER = LoggerFactory.getLogger(ApiRequestValidator.class);

	@Autowired
	JsonSchemaLoader jsonSchemaLoader;
	
	public ValidationMessage validateApiRequest(final String apiReqJson) {
		ValidationMessage requestValidationMssg =  new ValidationMessage();
		JsonSchema apiReqValidationSchema = null;
		ProcessingReport reqValidationReport;
		List<String> validationErrMssgList = new ArrayList<>();
		try {
			JsonNode jsonSchemaNode = jsonSchemaLoader.getRequestValidationSchema();
			apiReqValidationSchema = jsonSchemaLoader.getJsonSchemaFactory().getJsonSchema(jsonSchemaNode);
			LOGGER.debug("Got Validation Schema >>", apiReqValidationSchema);
			reqValidationReport = apiReqValidationSchema.validate(JsonLoader.fromString(apiReqJson), true);
			LOGGER.debug("Got Validation Report >>", reqValidationReport);
			if (null != reqValidationReport && !reqValidationReport.isSuccess()) {
				for (ProcessingMessage validationErrMssg : reqValidationReport) {
					LOGGER.debug("Error Level >>" + validationErrMssg.getLogLevel().name());
					validationErrMssgList.add(validationErrMssg.getMessage());
				}
				requestValidationMssg.setValidationStatus(reqValidationReport.isSuccess());
				requestValidationMssg.setValidationMessage(StringUtils.collectionToDelimitedString
						(validationErrMssgList, ","));
			}
			
		} catch (IOException | JsonReferenceException | ProcessingException excp) {
			LOGGER.error("validateApiRequest :: Error validting payload >>" + apiReqJson
					, ", Against Schema >>"+ apiReqValidationSchema.toString(), excp);
			excp.printStackTrace();
		}
		return requestValidationMssg;
		
	}

}

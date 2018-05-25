package subhasys.api.restful.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import subhasys.api.restful.domain.Customer;
import subhasys.api.restful.service.CustomerService;
import subhasys.api.restful.service.exception.CustomerExistsException;
import subhasys.api.restful.validation.ApiRequestValidator;
import subhasys.api.restful.validation.ValidationMessage;

import javax.inject.Inject;
import javax.validation.Valid;
import java.util.List;

@RestController
public class CustomerController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerController.class);
    private final CustomerService customerService;
    private final ApiRequestValidator apiRequestValidator;
    private final ObjectMapper objToJsonConverter = new ObjectMapper();

    @Inject
    public CustomerController(final CustomerService customerService,
    		final ApiRequestValidator apiRequestValidator) {
        this.customerService = customerService;
        this.apiRequestValidator = apiRequestValidator;
    }

	@RequestMapping(value = "/customer/add", method = RequestMethod.POST)
	public Object createUser(@RequestBody final Customer customer) {
		ValidationMessage apiReqValidationMssg = null;
		try {
			if (null != customer) {
				LOGGER.debug("Received request to create the {}" + customer);
				apiReqValidationMssg = apiRequestValidator.validateApiRequest(
						objToJsonConverter.writeValueAsString(customer));
			}

		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return (apiReqValidationMssg != null && apiReqValidationMssg.isValidationStatus()) ?
			   customerService.save(customer) : apiReqValidationMssg;
	}

    @RequestMapping(value = "/customer/list", method = RequestMethod.GET)
    public List<Customer> listUsers() {
        LOGGER.debug("Received request to list all Customers");
        return customerService.getList();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public String handleCustomerExistsException(CustomerExistsException excp) {
        return excp.getMessage();
    }

}

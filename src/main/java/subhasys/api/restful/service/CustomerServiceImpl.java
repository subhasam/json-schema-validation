package subhasys.api.restful.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import subhasys.api.restful.domain.Customer;
import subhasys.api.restful.repository.CustomerRepository;
import subhasys.api.restful.service.exception.CustomerExistsException;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Service
@Validated
public class CustomerServiceImpl implements CustomerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerServiceImpl.class);
    private final CustomerRepository repository;

    @Inject
    public CustomerServiceImpl(final CustomerRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public Customer save(@NotNull @Valid final Customer customer) {
        LOGGER.debug("Creating {}", customer);
        Customer existing = repository.findOne(customer.getCustomerid());
        if (existing != null) {
            throw new CustomerExistsException(
                    String.format("There already exists a customer with id=%s", customer.getCustomerid()));
        }
        return repository.save(customer);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Customer> getList() {
        LOGGER.debug("Retrieving the list of all customers");
        return repository.findAll();
    }

}

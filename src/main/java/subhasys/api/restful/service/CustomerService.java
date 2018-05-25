package subhasys.api.restful.service;

import java.util.List;

import subhasys.api.restful.domain.Customer;

public interface CustomerService {

    Customer save(Customer customer);

    List<Customer> getList();

}

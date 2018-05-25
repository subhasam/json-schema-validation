package subhasys.api.restful.controller;

import subhasys.api.restful.controller.CustomerController;
import subhasys.api.restful.domain.Customer;
import subhasys.api.restful.service.CustomerService;
import subhasys.api.restful.util.CustomerUtil;
import subhasys.api.restful.validation.ApiRequestValidator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CustomerControllerTest {

    @Mock
    private CustomerService customerService;
    @Mock
    private ApiRequestValidator apiReqValidator;

    private CustomerController cutomerController;

    @Before
    public void setUp() throws Exception {
        cutomerController = new CustomerController(customerService, apiReqValidator);
    }

    @Test
    public void shouldCreateUser() throws Exception {
        final Customer savedCustomer = stubServiceToReturnStoredUser();
        final Customer customer = CustomerUtil.createUser();
        Customer returnedCustomer = (Customer) cutomerController.createUser(customer);
        // verify user was passed to CustomerService
        verify(customerService, times(1)).save(customer);
        assertEquals("Returned user should come from the service", savedCustomer, returnedCustomer);
    }

    private Customer stubServiceToReturnStoredUser() {
        final Customer customer = CustomerUtil.createUser();
        when(customerService.save(any(Customer.class))).thenReturn(customer);
        return customer;
    }


    @Test
    public void shouldListAllUsers() throws Exception {
        stubServiceToReturnExistingUsers(10);
        Collection<Customer> users = cutomerController.listUsers();
        assertNotNull(users);
        assertEquals(10, users.size());
        // verify user was passed to CustomerService
        verify(customerService, times(1)).getList();
    }

    private void stubServiceToReturnExistingUsers(int howMany) {
        when(customerService.getList()).thenReturn(CustomerUtil.createUserList(howMany));
    }

}

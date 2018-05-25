package subhasys.api.restful.service;

import subhasys.api.restful.domain.Customer;
import subhasys.api.restful.repository.CustomerRepository;
import subhasys.api.restful.service.CustomerService;
import subhasys.api.restful.service.CustomerServiceImpl;
import subhasys.api.restful.service.exception.CustomerExistsException;
import subhasys.api.restful.util.CustomerUtil;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Collection;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CustomerServiceImplTest {

    @Mock
    private CustomerRepository customerRepository;

    private CustomerService customerService;

    @Before
    public void setUp() throws Exception {
        customerService = new CustomerServiceImpl(customerRepository);
    }

    @Test
    public void shouldSaveNewUser_GivenThereDoesNotExistOneWithTheSameId_ThenTheSavedUserShouldBeReturned() throws Exception {
        final Customer savedCustomer = stubRepositoryToReturnUserOnSave();
        final Customer customer = CustomerUtil.createUser();
        final Customer returnedCustomer = customerService.save(customer);
        // verify repository was called with user
        verify(customerRepository, times(1)).save(customer);
        assertEquals("Returned Customer should come from the repository", savedCustomer, returnedCustomer);
    }

    private Customer stubRepositoryToReturnUserOnSave() {
        Customer customer = CustomerUtil.createUser();
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);
        return customer;
    }

    @Test
    public void shouldSaveNewUser_GivenThereExistsOneWithTheSameId_ThenTheExceptionShouldBeThrown() throws Exception {
        stubRepositoryToReturnExistingUser();
        try {
            customerService.save(CustomerUtil.createUser());
            fail("Expected exception");
        } catch (CustomerExistsException ignored) {
        }
        verify(customerRepository, never()).save(any(Customer.class));
    }

    private void stubRepositoryToReturnExistingUser() {
        final Customer customer = CustomerUtil.createUser();
        when(customerRepository.findOne(customer.getCustomerid())).thenReturn(customer);
    }

    @Test
    public void shouldListAllUsers_GivenThereExistSome_ThenTheCollectionShouldBeReturned() throws Exception {
        stubRepositoryToReturnExistingUsers(10);
        Collection<Customer> list = customerService.getList();
        assertNotNull(list);
        assertEquals(10, list.size());
        verify(customerRepository, times(1)).findAll();
    }

    private void stubRepositoryToReturnExistingUsers(int howMany) {
        when(customerRepository.findAll()).thenReturn(CustomerUtil.createUserList(howMany));
    }

    @Test
    public void shouldListAllUsers_GivenThereNoneExist_ThenTheEmptyCollectionShouldBeReturned() throws Exception {
        stubRepositoryToReturnExistingUsers(0);
        Collection<Customer> list = customerService.getList();
        assertNotNull(list);
        assertTrue(list.isEmpty());
        verify(customerRepository, times(1)).findAll();
    }

}

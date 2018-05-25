package subhasys.api.restful.util;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Objects;

import subhasys.api.restful.domain.Customer;

public class CustomerUtil {

    private static final String ID = "customerid";
    private static final String PASSWORD = "password";
    private static final String EMAIL = "email";

    private CustomerUtil() {
    }

    public static Customer createUser() {
        return new Customer(ID, PASSWORD, EMAIL);
    }

    public static List<Customer> createUserList(int howMany) {
        List<Customer> customerList = new ArrayList<>();
        for (int i = 0; i < howMany; i++) {
         	customerList.add(new Customer(ID + "#" + i, PASSWORD, EMAIL));
        }
        return customerList;
    }
    
	/*public static void main(String args[]) {
		List<Customer> customerList = new ArrayList<>();
		customerList.add(new Customer("12345", PASSWORD,"subhasys@mail.com"));
		try {
			System.out.print(new ObjectMapper().writeValueAsString(customerList));
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/

}

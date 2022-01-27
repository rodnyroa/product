package com.example.product.unit.customer;

import com.example.product.core.customer.domain.Customer;
import com.example.product.core.customer.dto.CustomerDto;

public class CustomerUtil {

    protected final Integer ID = 1;

    protected Customer getCustomerMock() {
        Customer customer = new Customer();
        customer.setId(ID);
        customer.setFirstName("Test");
        customer.setLastName("last name");
        customer.setEmail("test@example.com");
        return customer;
    }

    protected CustomerDto getCustomerDtoMock() {
        CustomerDto customer = new CustomerDto();
        customer.setId(ID);
        customer.setFirstName("Test");
        customer.setLastName("last name");
        customer.setEmail("test@example.com");
        return customer;
    }
}

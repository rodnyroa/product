package com.example.product.unit.customer;

import com.example.product.core.customer.domain.Customer;
import com.example.product.core.customer.dto.CustomerDto;
import com.example.product.core.customer.service.CustomerMapper;
import com.example.product.core.customer.service.CustomerMapperImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("Customer Mapper")
@ExtendWith(MockitoExtension.class)
public class CustomerMapperTest extends CustomerUtil {

    private CustomerMapper mapper;

    @BeforeEach
    void setup() {
        this.mapper = new CustomerMapperImpl();
    }

    @DisplayName("dtoToEntity")
    @Test
    void dtoToEntity() {
        CustomerDto dto = this.getCustomerDtoMock();

        Customer customer = this.mapper.dtoToEntity(dto);

        Assertions.assertNotNull(customer);
        Assertions.assertTrue(dto.getId().equals(customer.getId()));
        Assertions.assertTrue(dto.getFirstName().equals(customer.getFirstName()));
        Assertions.assertTrue(dto.getLastName().equals(customer.getLastName()));
        Assertions.assertTrue(dto.getEmail().equals(customer.getEmail()));
    }

    @DisplayName("entityToDto")
    @Test
    void entityToDto() {

        Customer entity = this.getCustomerMock();

        CustomerDto customer = this.mapper.entityToDto(entity);

        Assertions.assertNotNull(customer);
        Assertions.assertTrue(entity.getId().equals(customer.getId()));
        Assertions.assertTrue(entity.getFirstName().equals(customer.getFirstName()));
        Assertions.assertTrue(entity.getLastName().equals(customer.getLastName()));
        Assertions.assertTrue(entity.getEmail().equals(customer.getEmail()));

    }
}

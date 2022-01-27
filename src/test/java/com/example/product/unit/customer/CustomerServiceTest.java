package com.example.product.unit.customer;

import com.example.product.core.customer.domain.Customer;
import com.example.product.core.customer.dto.CustomerDto;
import com.example.product.core.customer.service.CustomerMapper;
import com.example.product.core.customer.service.CustomerSeeker;
import com.example.product.core.customer.service.CustomerService;
import com.example.product.core.customer.service.impl.CustomerServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;

@DisplayName("Customer Service")
@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest extends CustomerUtil {


    private CustomerService service;

    @Mock
    private CustomerSeeker seeker;
    @Mock
    private CustomerMapper mapper;

    private InOrder inOrder;

    @BeforeEach
    void setup() {
        this.service = new CustomerServiceImpl(this.seeker, this.mapper);
        this.inOrder = Mockito.inOrder(this.seeker, this.mapper);
    }


    @DisplayName("get customers")
    @Test
    void getCustomers() {

        Page<Customer> mock = new PageImpl<>(Arrays.asList(this.getCustomerMock()));

        Mockito.when(this.seeker.findAll(Mockito.any(Pageable.class)))
                .thenReturn(mock);

        Page<CustomerDto> page = this.service.findAll(PageRequest.of(1, 10));

        Assertions.assertNotNull(page);
        Assertions.assertEquals(mock.getContent().size(), page.getContent().size());

        this.inOrder.verify(this.seeker).findAll(Mockito.any(PageRequest.class));
        this.inOrder.verify(this.mapper).entityToDto(Mockito.any(Customer.class));
        this.inOrder.verifyNoMoreInteractions();
    }

}

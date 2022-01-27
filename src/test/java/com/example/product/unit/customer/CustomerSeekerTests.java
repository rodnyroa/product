package com.example.product.unit.customer;

import com.example.product.core.customer.domain.Customer;
import com.example.product.core.customer.repository.CustomerRepository;
import com.example.product.core.customer.service.CustomerSeeker;
import com.example.product.core.customer.service.impl.CustomerSeekerImpl;
import com.example.product.core.exception.NotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

@DisplayName("Product Seeker")
@ExtendWith(MockitoExtension.class)
public class CustomerSeekerTests extends CustomerUtil {

    private CustomerSeeker seeker;

    @Mock
    private CustomerRepository repository;

    @BeforeEach
    void setup() {
        this.seeker = new CustomerSeekerImpl(repository);
    }

    @DisplayName("when call findAll then return items")
    @Test
    void findAll() {

        Page<Customer> mock = new PageImpl<>(Arrays.asList(this.getCustomerMock()));
        Mockito.when(this.repository.findAll(Mockito.any(PageRequest.class)))
                .thenReturn(mock);
        Page<Customer> page = this.seeker.findAll(PageRequest.of(1, 10));
        Assertions.assertNotNull(page);
        Assertions.assertEquals(mock, page);
    }

    @DisplayName("findAll with no page request")
    @Test
    void findAll01() {
        Page<Customer> mock = new PageImpl<>(Collections.emptyList());
        Mockito.when(this.repository.findAll(Mockito.any(PageRequest.class)))
                .thenReturn(mock);

        Page<Customer> page = this.seeker.findAll(PageRequest.of(1, 10));

        Assertions.assertNotNull(page);
        Assertions.assertFalse(page.hasContent());
    }

    @DisplayName("findById returns value")
    @Test
    void findById() {
        Customer mock = this.getCustomerMock();
        Mockito.when(this.repository.findById(Mockito.anyInt()))
                .thenReturn(Optional.of(mock));
        Customer customer = this.seeker.findById(ID);
        Assertions.assertNotNull(customer);

    }

    @DisplayName("when findById with invalid id should throw NotFoundException")
    @Test
    void findByIdShouldThrowException() {
        Mockito.when(this.repository.findById(Mockito.anyInt()))
                .thenReturn(Optional.empty());
        Assertions.assertThrows(NotFoundException.class, () -> this.seeker.findById(ID));
    }

}

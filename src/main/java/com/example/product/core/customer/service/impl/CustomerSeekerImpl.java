package com.example.product.core.customer.service.impl;

import com.example.product.core.customer.domain.Customer;
import com.example.product.core.customer.repository.CustomerRepository;
import com.example.product.core.customer.service.CustomerSeeker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class CustomerSeekerImpl implements CustomerSeeker {

    private CustomerRepository repository;

    @Autowired
    public CustomerSeekerImpl(CustomerRepository repository) {
        this.repository = repository;
    }

    @Override
    public Page<Customer> findAll(Pageable pageable) {
        return this.repository.findAll(pageable);
    }
}

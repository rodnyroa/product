package com.example.product.core.customer.service;

import com.example.product.core.customer.domain.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomerSeeker {

    Page<Customer> findAll(Pageable pageable);
}

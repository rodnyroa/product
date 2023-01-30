package com.example.product.core.customer.service;

import com.example.product.core.customer.domain.Customer;
import com.example.product.core.customer.repository.CustomerRepository;
import com.example.product.core.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class CustomerSeeker {

  private CustomerRepository repository;

  @Autowired
  public CustomerSeeker(CustomerRepository repository) {
    this.repository = repository;
  }


  public Page<Customer> findAll(Pageable pageable) {
    return this.repository.findAll(pageable);
  }


  public Customer findById(Integer customerId) {
    String msg = String.format("Invalid id: %s", customerId);
    return this.repository.findById(customerId)
        .orElseThrow(() -> new NotFoundException(msg));
  }
}

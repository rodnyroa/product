package com.example.product.core.customer.service;

import com.example.product.core.customer.domain.Customer;
import com.example.product.core.customer.dto.CustomerDto;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

  private CustomerSeeker seeker;
  private CustomerMapper mapper;

  @Autowired
  public CustomerService(CustomerSeeker seeker, CustomerMapper mapper) {
    this.seeker = seeker;
    this.mapper = mapper;
  }


  public Page<CustomerDto> findAll(Pageable pageable) {
    Page<Customer> entities = this.seeker.findAll(pageable);
    List<CustomerDto> dtos = entities
        .getContent()
        .stream()
        .map(this.mapper::entityToDto)
        .collect(Collectors.toList());
    return new PageImpl<>(dtos, pageable, entities.getTotalElements());
  }
}

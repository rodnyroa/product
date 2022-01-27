package com.example.product.core.customer.service;

import com.example.product.core.customer.dto.CustomerDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomerService {

    Page<CustomerDto> findAll(Pageable pageable);

}

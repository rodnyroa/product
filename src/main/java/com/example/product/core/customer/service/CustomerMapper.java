package com.example.product.core.customer.service;

import com.example.product.core.customer.domain.Customer;
import com.example.product.core.customer.dto.CustomerDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    Customer dtoToEntity(CustomerDto dto);

    CustomerDto entityToDto(Customer entity);
}

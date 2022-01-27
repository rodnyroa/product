package com.example.product.core.product.service;

import com.example.product.core.product.domain.Product;
import com.example.product.core.product.dto.ProductDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    Product dtoToEntity(ProductDto dto);

    ProductDto entityToDto(Product entity);
}

package com.example.product.core.product.service;

import com.example.product.core.product.dto.ProductDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {

    Page<ProductDto> findAll(Pageable pageable);

    ProductDto findById(Integer productId);

    void update(Integer productId, ProductDto productDto);
}

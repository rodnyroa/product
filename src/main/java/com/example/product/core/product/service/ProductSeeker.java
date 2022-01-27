package com.example.product.core.product.service;

import com.example.product.core.product.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductSeeker {

    Page<Product> findAll(Pageable pageable);

    Product findById(Integer productId);
}

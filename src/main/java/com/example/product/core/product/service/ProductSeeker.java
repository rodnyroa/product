package com.example.product.core.product.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.example.product.core.product.domain.Product;

public interface ProductSeeker {

    Page<Product> findAll(Pageable pageable);

    Product findById(Integer productId);
}

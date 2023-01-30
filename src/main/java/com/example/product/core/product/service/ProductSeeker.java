package com.example.product.core.product.service;

import com.example.product.core.exception.NotFoundException;
import com.example.product.core.product.domain.Product;
import com.example.product.core.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class ProductSeeker implements ProductSeeker {

    private ProductRepository repository;

    @Autowired
    public ProductSeeker(ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public Page<Product> findAll(Pageable pageable) {
        return this.repository.findAll(pageable);
    }

    @Override
    public Product findById(Integer productId) {
        String msg = String.format("Invalid id: %s", productId);
        return this.repository.findById(productId)
                .orElseThrow(() -> new NotFoundException(msg));
    }
}

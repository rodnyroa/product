package com.example.product.core.product.service.impl;

import com.example.product.core.product.domain.Product;
import com.example.product.core.product.dto.ProductDto;
import com.example.product.core.product.repository.ProductRepository;
import com.example.product.core.product.service.ProductMapper;
import com.example.product.core.product.service.ProductSeeker;
import com.example.product.core.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private ProductSeeker seeker;
    private ProductMapper mapper;
    private ProductRepository repository;

    @Autowired
    public ProductServiceImpl(ProductSeeker seeker,
                              ProductMapper mapper,
                              ProductRepository repository) {
        this.seeker = seeker;
        this.mapper = mapper;
        this.repository = repository;
    }

    @Override
    public Page<ProductDto> findAll(Pageable pageable) {
        Page<Product> entities = this.seeker.findAll(pageable);
        List<ProductDto> dtos = entities
                .getContent()
                .stream()
                .map(this.mapper::entityToDto)
                .collect(Collectors.toList());
        return new PageImpl<>(dtos, pageable, entities.getTotalElements());
    }

    @Override
    public ProductDto findById(Integer productId) {
        Product entity = this.findProductById(productId);
        return this.mapper.entityToDto(entity);
    }

    @Override
    public void update(Integer productId, ProductDto productDto) {
        Product product = this.findProductById(productId);
        Product productUpdated = this.mapper.dtoToEntity(productDto);
        productUpdated.setId(product.getId());

        this.save(productUpdated);

    }

    @Override
    public Integer add(ProductDto productDto) {

        Product product = this.mapper.dtoToEntity(productDto);
        product.setId(null);
        return this.save(product).getId();
    }

    private Product save(Product product) {
        return this.repository.save(product);
    }

    private Product findProductById(Integer productId) {
        return this.seeker.findById(productId);
    }
}

package com.example.product.unit.product;

import com.example.product.core.product.domain.Product;
import com.example.product.core.product.dto.ProductDto;

import java.math.BigDecimal;

public class ProductUtil {

    protected final Integer ID = 1;

    protected Product getProductMock() {
        Product product = new Product();
        product.setId(ID);
        product.setName("Test");
        product.setPrice(new BigDecimal(12.2));
        return product;
    }

    protected ProductDto getProductDtoMock() {
        ProductDto product = new ProductDto();
        product.setId(ID);
        product.setName("Test");
        product.setPrice(new BigDecimal(12.2));
        return product;
    }
}

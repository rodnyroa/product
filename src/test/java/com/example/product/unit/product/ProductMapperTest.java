package com.example.product.unit.product;

import com.example.product.core.product.domain.Product;
import com.example.product.core.product.dto.ProductDto;
import com.example.product.core.product.service.ProductMapper;
import com.example.product.core.product.service.ProductMapperImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("Product Mapper")
@ExtendWith(MockitoExtension.class)
public class ProductMapperTest extends ProductUtil {

    private ProductMapper mapper;

    @BeforeEach
    void setup() {
        this.mapper = new ProductMapperImpl();
    }

    @DisplayName("dtoToEntity")
    @Test
    void dtoToEntity() {
        ProductDto dto = this.getProductDtoMock();

        Product product = this.mapper.dtoToEntity(dto);

        Assertions.assertNotNull(product);
        Assertions.assertTrue(dto.getId().equals(product.getId()));
        Assertions.assertTrue(dto.getName().equals(product.getName()));
        Assertions.assertTrue(dto.getPrice().equals(product.getPrice()));
    }

    @DisplayName("entityToDto")
    @Test
    void entityToDto() {

        Product entity = this.getProductMock();

        ProductDto product = this.mapper.entityToDto(entity);

        Assertions.assertNotNull(product);
        Assertions.assertTrue(entity.getId().equals(product.getId()));
        Assertions.assertTrue(entity.getName().equals(product.getName()));
        Assertions.assertTrue(entity.getPrice().equals(product.getPrice()));

    }
}

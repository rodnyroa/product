package com.example.product.unit.product;

import com.example.product.core.exception.NotFoundException;
import com.example.product.core.product.domain.Product;
import com.example.product.core.product.repository.ProductRepository;
import com.example.product.core.product.service.ProductSeeker;
import com.example.product.core.product.service.impl.ProductSeekerImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

@DisplayName("Product Seeker")
@ExtendWith(MockitoExtension.class)
public class ProductSeekerTests extends ProductUtil {

    private ProductSeeker seeker;

    @Mock
    private ProductRepository repository;

    @BeforeEach
    void setup() {
        this.seeker = new ProductSeekerImpl(repository);
    }

    @DisplayName("findById returns value")
    @Test
    void findById() {
        Product mock = getProductMock();
        Mockito.when(repository.findById(Mockito.anyInt()))
                .thenReturn(Optional.of(mock));
        Product product = seeker.findById(ID);
        Assertions.assertNotNull(product);

    }

    @DisplayName("when findById with invalid id should throw NotFoundException")
    @Test
    void findByIdShouldThrowException() {
        Mockito.when(repository.findById(Mockito.anyInt()))
                .thenReturn(Optional.empty());
        Assertions.assertThrows(NotFoundException.class, () -> seeker.findById(ID));
    }

    @DisplayName("when call findAll then return items")
    @Test
    void findAllWi() {

        Page<Product> mock = new PageImpl<>(Arrays.asList(this.getProductMock()));
        Mockito.when(repository.findAll(Mockito.any(PageRequest.class)))
                .thenReturn(mock);
        Page<Product> page = seeker.findAll(PageRequest.of(1, 10));
        Assertions.assertNotNull(page);
        Assertions.assertEquals(mock, page);
    }

    @DisplayName("findAll with no page request")
    @Test
    void findAll01() {
        Page<Product> mock = new PageImpl<>(Collections.emptyList());
        Mockito.when(repository.findAll(Mockito.any(PageRequest.class)))
                .thenReturn(mock);

        Page<Product> page = this.seeker.findAll(PageRequest.of(1, 10));

        Assertions.assertNotNull(page);
        Assertions.assertFalse(page.hasContent());
    }

}

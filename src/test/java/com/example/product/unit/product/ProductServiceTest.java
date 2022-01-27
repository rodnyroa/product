package com.example.product.unit.product;

import com.example.product.core.product.domain.Product;
import com.example.product.core.product.dto.ProductDto;
import com.example.product.core.product.repository.ProductRepository;
import com.example.product.core.product.service.ProductMapper;
import com.example.product.core.product.service.ProductSeeker;
import com.example.product.core.product.service.ProductService;
import com.example.product.core.product.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;

@DisplayName("Product Service")
@ExtendWith(MockitoExtension.class)
public class ProductServiceTest extends ProductUtil {


    private ProductService service;

    @Mock
    private ProductSeeker seeker;
    @Mock
    private ProductMapper mapper;
    @Mock
    private ProductRepository repository;

    private InOrder inOrder;

    @BeforeEach
    void setup() {
        this.service = new ProductServiceImpl(this.seeker, this.mapper, this.repository);
        this.inOrder = Mockito.inOrder(this.seeker, this.mapper, this.repository);
    }


    @DisplayName("find all products")
    @Test
    void getCampaignWithElements() {

        Page<Product> mock = new PageImpl<>(Arrays.asList(this.getProductMock()));

        Mockito.when(this.seeker.findAll(Mockito.any(Pageable.class)))
                .thenReturn(mock);

        Page<ProductDto> page = this.service.findAll(PageRequest.of(1, 10));

        Assertions.assertNotNull(page);
        Assertions.assertEquals(mock.getContent().size(), page.getContent().size());

        this.inOrder.verify(this.seeker).findAll(Mockito.any(PageRequest.class));
        this.inOrder.verify(this.mapper).entityToDto(Mockito.any(Product.class));
        this.inOrder.verifyNoMoreInteractions();
    }

    @DisplayName("findById successful")
    @Test
    void findById() {
        Product mock = this.getProductMock();

        Mockito.when(this.seeker.findById(Mockito.anyInt()))
                .thenReturn(mock);
        Mockito.when(this.mapper.entityToDto(Mockito.any(Product.class)))
                .thenReturn(this.getProductDtoMock());


        ProductDto dto = this.service.findById(ID);

        Assertions.assertNotNull(dto);
        Assertions.assertNotNull(dto.getId());
        Assertions.assertNotNull(dto.getName());
        Assertions.assertNotNull(dto.getPrice());

        this.inOrder.verify(this.seeker).findById(Mockito.anyInt());
        this.inOrder.verify(this.mapper).entityToDto(Mockito.any(Product.class));
        this.inOrder.verifyNoMoreInteractions();
    }

    @DisplayName("update successful")
    @Test
    void update() {

        Product mock = this.getProductMock();

        Mockito.when(this.seeker.findById(Mockito.anyInt()))
                .thenReturn(mock);

        Mockito.when(this.mapper.dtoToEntity(Mockito.any(ProductDto.class)))
                .thenReturn(this.getProductMock());

        Mockito.when(this.repository.save(Mockito.any(Product.class)))
                .thenAnswer(i -> i.getArguments()[0]);

        ProductDto request = this.getProductDtoMock();

        this.service.update(ID, request);

        this.inOrder.verify(this.seeker).findById(Mockito.anyInt());
        this.inOrder.verify(this.mapper).dtoToEntity(Mockito.any(ProductDto.class));
        this.inOrder.verify(this.repository).save(Mockito.any(Product.class));
        this.inOrder.verifyNoMoreInteractions();

    }


}

package com.example.product.integration;

import com.example.product.ProductApplication;
import com.example.product.core.product.dto.ProductDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.List;

@DisplayName("Product Controller")
@ActiveProfiles("dev")
@SpringBootTest(classes = ProductApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductControllerTest {

    private static final String URL = "/v1/products";

    private TestRestTemplate restTemplate;

    @Autowired
    public ProductControllerTest(TestRestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @DisplayName("Get products successful")
    @Test
    void getProducts() {
        HttpHeaders HEADERS = new HttpHeaders();
        HEADERS.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Void> requestEntity = new HttpEntity<>(null, HEADERS);

        ResponseEntity<RestPageImpl<ProductDto>> response = restTemplate
                .exchange(URL, HttpMethod.GET, requestEntity, new ParameterizedTypeReference<RestPageImpl<ProductDto>>() {
                });

        Validator.checkHttpStatus(HttpStatus.OK, response.getStatusCode());

        RestPageImpl<ProductDto> page = response.getBody();
        List<ProductDto> list = page.getContent();

        Assertions.assertFalse(list.isEmpty());

        ProductDto product = list.get(0);

        Assertions.assertNotNull(product.getId());
        Assertions.assertNotNull(product.getName());
        Assertions.assertNotNull(product.getPrice());
    }

    @DisplayName("Get product by id successful")
    @Test
    void getProductById() {

        HttpHeaders HEADERS = new HttpHeaders();
        HEADERS.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Void> requestEntity = new HttpEntity<>(null, HEADERS);

        ResponseEntity<ProductDto> response = restTemplate
                .exchange(URL.concat("/{id}"), HttpMethod.GET, requestEntity, new ParameterizedTypeReference<ProductDto>() {
                }, 1);

        Validator.checkHttpStatus(HttpStatus.OK, response.getStatusCode());

        ProductDto product = response.getBody();

        Assertions.assertNotNull(product.getId());
        Assertions.assertNotNull(product.getName());
        Assertions.assertNotNull(product.getPrice());
    }


    @DisplayName("Create new product with invalid request should return 400")
    @Test
    void createNewProductWithInvalidRequest() {

        HttpHeaders HEADERS = new HttpHeaders();
        HEADERS.setContentType(MediaType.APPLICATION_JSON);

        ProductDto request = new ProductDto();
        request.setName("AA");

        HttpEntity<ProductDto> requestEntity = new HttpEntity<>(request, HEADERS);

        ResponseEntity<Void> response = restTemplate
                .exchange(URL, HttpMethod.POST, requestEntity, new ParameterizedTypeReference<Void>() {
                }, 1);

        Validator.checkHttpStatus(HttpStatus.BAD_REQUEST, response.getStatusCode());

    }

    @DisplayName("Create new product successful")
    @Test
    void createNewProduct() {

        HttpHeaders HEADERS = new HttpHeaders();
        HEADERS.setContentType(MediaType.APPLICATION_JSON);

        ProductDto request = new ProductDto();
        request.setName("AA");
        request.setPrice(new BigDecimal(12));

        HttpEntity<ProductDto> requestEntity = new HttpEntity<>(request, HEADERS);

        ResponseEntity<Void> response = restTemplate
                .exchange(URL, HttpMethod.POST, requestEntity, new ParameterizedTypeReference<Void>() {
                }, 1);

        Validator.checkHttpStatus(HttpStatus.CREATED, response.getStatusCode());
        Validator.checkCreatedSuccessful(response, URL);

    }

    @DisplayName("Update product successful")
    @Test
    void updateProductPriceSuccessful() {

        HttpHeaders HEADERS = new HttpHeaders();
        HEADERS.setContentType(MediaType.APPLICATION_JSON);

        ProductDto request = new ProductDto();
        request.setName("AA");
        request.setPrice(new BigDecimal(12));

        HttpEntity<ProductDto> requestEntity = new HttpEntity<>(request, HEADERS);

        ResponseEntity<Void> response = restTemplate
                .exchange(URL.concat("/{id}"), HttpMethod.PUT, requestEntity, new ParameterizedTypeReference<Void>() {
                }, 1);

        Validator.checkHttpStatus(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @DisplayName("Update product with invalid product id should return 404")
    @Test
    void updateProductPrice() {

        HttpHeaders HEADERS = new HttpHeaders();
        HEADERS.setContentType(MediaType.APPLICATION_JSON);

        ProductDto request = new ProductDto();
        request.setName("AA");
        request.setPrice(new BigDecimal(12));

        HttpEntity<ProductDto> requestEntity = new HttpEntity<>(request, HEADERS);

        ResponseEntity<Void> response = restTemplate
                .exchange(URL.concat("/{id}"), HttpMethod.PUT, requestEntity, new ParameterizedTypeReference<Void>() {
                }, 1111);

        Validator.checkHttpStatus(HttpStatus.NOT_FOUND, response.getStatusCode());
    }


}

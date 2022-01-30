package com.example.product.integration;

import com.example.product.ProductApplication;
import com.example.product.core.order.dto.ItemDto;
import com.example.product.core.order.dto.OrderRequestDto;
import com.example.product.core.order.dto.OrderResponseDto;
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
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@DisplayName("Order Controller")
@ActiveProfiles("dev")
@SpringBootTest(classes = ProductApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OrderControllerTest {

    private static final String URL = "/v1/customers/%s/orders";
    private static final String CUSTOMER_ID = "1";

    private TestRestTemplate restTemplate;

    @Autowired
    public OrderControllerTest(TestRestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @DisplayName("Create order successful")
    @Test
    void createOrderSuccessful() {

        HttpHeaders HEADERS = new HttpHeaders();
        HEADERS.setContentType(MediaType.APPLICATION_JSON);

        OrderRequestDto request = new OrderRequestDto();

        ItemDto item = ItemDto.builder()
                .id(1)
                .build();

        ItemDto item2 = ItemDto.builder()
                .id(2)
                .quantity(2)
                .build();

        request.setProducts(Arrays.asList(item, item2));

        HttpEntity<OrderRequestDto> requestEntity = new HttpEntity<>(request, HEADERS);

        String url = String.format(URL, CUSTOMER_ID);

        ResponseEntity<Void> response = restTemplate
                .exchange(url, HttpMethod.POST, requestEntity, new ParameterizedTypeReference<Void>() {
                });

        Validator.checkHttpStatus(HttpStatus.CREATED, response.getStatusCode());
        Validator.checkCreatedSuccessful(response, url);
    }

    @DisplayName("Create order with invalid request shloud return 400")
    @Test
    void createOrderWithInvalidRequest() {

        HttpHeaders HEADERS = new HttpHeaders();
        HEADERS.setContentType(MediaType.APPLICATION_JSON);

        OrderRequestDto request = new OrderRequestDto();

        HttpEntity<OrderRequestDto> requestEntity = new HttpEntity<>(request, HEADERS);

        String url = String.format(URL, CUSTOMER_ID);

        ResponseEntity<Void> response = restTemplate
                .exchange(url, HttpMethod.POST, requestEntity, new ParameterizedTypeReference<Void>() {
                });

        Validator.checkHttpStatus(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @DisplayName("Create order with invalid customer id should return 404")
    @Test
    void createOrderWithInvalidCustomerIdShouldReturn404() {

        HttpHeaders HEADERS = new HttpHeaders();
        HEADERS.setContentType(MediaType.APPLICATION_JSON);

        OrderRequestDto request = new OrderRequestDto();

        ItemDto item = ItemDto.builder()
                .id(1)
                .build();

        ItemDto item2 = ItemDto.builder()
                .id(2)
                .quantity(2)
                .build();

        request.setProducts(Arrays.asList(item, item2));

        HttpEntity<OrderRequestDto> requestEntity = new HttpEntity<>(request, HEADERS);

        String url = String.format(URL, 888);

        ResponseEntity<Void> response = restTemplate
                .exchange(url, HttpMethod.POST, requestEntity, new ParameterizedTypeReference<Void>() {
                });

        Validator.checkHttpStatus(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @DisplayName("Add products to created order successful")
    @Test
    void addProduct2CreatedOrder01() {

        HttpHeaders HEADERS = new HttpHeaders();
        HEADERS.setContentType(MediaType.APPLICATION_JSON);

        OrderRequestDto request = new OrderRequestDto();

        ItemDto item = ItemDto.builder()
                .id(1)
                .build();

        ItemDto item2 = ItemDto.builder()
                .id(2)
                .quantity(2)
                .build();

        request.setProducts(Arrays.asList(item, item2));

        HttpEntity<OrderRequestDto> requestEntity = new HttpEntity<>(request, HEADERS);

        String url = String.format(URL, CUSTOMER_ID).concat("/253dd095-57bf-45bd-92bf-fa005b03d1dd");

        ResponseEntity<Void> response = restTemplate
                .exchange(url, HttpMethod.POST, requestEntity, new ParameterizedTypeReference<Void>() {
                });

        Validator.checkHttpStatus(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @DisplayName("Add products to created order with invalid order number should return 404")
    @Test
    void addProduct2CreatedOrder02() {

        HttpHeaders HEADERS = new HttpHeaders();
        HEADERS.setContentType(MediaType.APPLICATION_JSON);

        OrderRequestDto request = new OrderRequestDto();

        ItemDto item = ItemDto.builder()
                .id(1)
                .build();

        ItemDto item2 = ItemDto.builder()
                .id(2)
                .quantity(2)
                .build();

        request.setProducts(Arrays.asList(item, item2));

        HttpEntity<OrderRequestDto> requestEntity = new HttpEntity<>(request, HEADERS);

        String url = String.format(URL, CUSTOMER_ID).concat("/253dd095-57bf-45bd-92bf-fa005b03d1da");

        ResponseEntity<Void> response = restTemplate
                .exchange(url, HttpMethod.POST, requestEntity, new ParameterizedTypeReference<Void>() {
                });

        Validator.checkHttpStatus(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @DisplayName("Add products to created order with invalid customer id should return 404")
    @Test
    void addProduct2CreatedOrder03() {

        HttpHeaders HEADERS = new HttpHeaders();
        HEADERS.setContentType(MediaType.APPLICATION_JSON);

        OrderRequestDto request = new OrderRequestDto();

        ItemDto item = ItemDto.builder()
                .id(1)
                .build();

        ItemDto item2 = ItemDto.builder()
                .id(2)
                .quantity(2)
                .build();

        request.setProducts(Arrays.asList(item, item2));

        HttpEntity<OrderRequestDto> requestEntity = new HttpEntity<>(request, HEADERS);

        String url = String.format(URL, 9999).concat("/253dd095-57bf-45bd-92bf-fa005b03d1dd");

        ResponseEntity<Void> response = restTemplate
                .exchange(url, HttpMethod.POST, requestEntity, new ParameterizedTypeReference<Void>() {
                });

        Validator.checkHttpStatus(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @DisplayName("Get orders successful")
    @Test
    void getOrders() {
        HttpHeaders HEADERS = new HttpHeaders();
        HEADERS.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Void> requestEntity = new HttpEntity<>(null, HEADERS);

        String url = "/v1/orders";

        ResponseEntity<RestPageImpl<OrderResponseDto>> response = restTemplate
                .exchange(url, HttpMethod.GET, requestEntity, new ParameterizedTypeReference<RestPageImpl<OrderResponseDto>>() {
                });

        Validator.checkHttpStatus(HttpStatus.OK, response.getStatusCode());

        RestPageImpl<OrderResponseDto> page = response.getBody();
        List<OrderResponseDto> list = page.getContent();

        Assertions.assertFalse(list.isEmpty());

        OrderResponseDto order = list.get(0);

        Assertions.assertNotNull(order.getBuyer());
        Assertions.assertNotNull(order.getOrderNumber());
        Assertions.assertNotNull(order.getOrderTime());
        Assertions.assertNotNull(order.getTotalAmount());
        Assertions.assertNotNull(order.getProducts());

        List<ItemDto> products = order.getProducts();

        AtomicReference<BigDecimal> totalAmount = new AtomicReference<>(new BigDecimal(0));

        products.stream()
                .forEach(product -> {
                    BigDecimal totalLine = new BigDecimal(product.getQuantity()).multiply(product.getPrice());
                    totalAmount.updateAndGet(total -> total.add(totalLine));
                });

        Assertions.assertTrue(totalAmount.get().equals(order.getTotalAmount()));

    }
}

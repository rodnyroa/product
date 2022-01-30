package com.example.product.integration;

import com.example.product.ProductApplication;
import com.example.product.core.customer.dto.CustomerDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@DisplayName("Customer Controller")
@ActiveProfiles("dev")
@SpringBootTest(classes = ProductApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CustomerControllerTest {

    private static final String URL = "/v1/customers";

    private TestRestTemplate restTemplate;

    @Autowired
    public CustomerControllerTest(TestRestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @DisplayName("Get orders successful")
    @Test
    void getCustomers() {
        HttpHeaders HEADERS = new HttpHeaders();
        HEADERS.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Void> requestEntity = new HttpEntity<>(null, HEADERS);

        ResponseEntity<RestPageImpl<CustomerDto>> response = restTemplate
                .exchange(URL, HttpMethod.GET, requestEntity, new ParameterizedTypeReference<RestPageImpl<CustomerDto>>() {
                });

        Validator.checkHttpStatus(HttpStatus.OK, response.getStatusCode());

        RestPageImpl<CustomerDto> page = response.getBody();
        List<CustomerDto> list = page.getContent();

        Assertions.assertFalse(list.isEmpty());

        CustomerDto customer = list.get(0);

        Assertions.assertNotNull(customer.getId());
        Assertions.assertNotNull(customer.getEmail());
        Assertions.assertNotNull(customer.getFirstName());
        Assertions.assertNotNull(customer.getLastName());

    }
}

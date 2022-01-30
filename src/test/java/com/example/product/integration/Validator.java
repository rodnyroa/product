package com.example.product.integration;

import org.junit.jupiter.api.Assertions;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.URI;

public class Validator {

    private Validator() {
    }

    public static void checkHttpStatus(HttpStatus expected, HttpStatus actual) {
        String msg = "http code incorrect -->    expected(" + expected + ")   actual(" + actual + ")";
        Assertions.assertTrue(expected.equals(actual), msg);
    }

    public static void checkCreatedSuccessful(ResponseEntity<Void> response, String url) {
        checkHttpStatus(HttpStatus.CREATED, response.getStatusCode());
        Assertions.assertNull(response.getBody(), "response body isn't null:\n" + response.getBody());
        Validator.checkHeaderLocation(response.getHeaders().getLocation(), url);
    }

    public static void checkHeaderLocation(URI location, String path) {
        Assertions.assertNotNull(location, "Location header missing");
        Assertions.assertTrue(location.getPath().contains(path), "Invalid location header: \n" + location.getPath());
    }
}

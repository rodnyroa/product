package com.example.product.config.controller;


public enum Messages implements Message {
    ENTITY_ALREADY_EXISTS("An element with the same id already exists", "00004"),
    BAD_REQUEST("The element is wrong or malformed", "400"),
    NOT_FOUND("Element not found", "404");

    private final String message;
    private final String code;

    Messages(String message, String code) {
        this.message = message;
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public String getCode() {
        return code;
    }
}

package com.example.product.config.controller;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResource {

    private String code;
    private String message;

    public ErrorResource() {
        super();
    }

    public ErrorResource(Message message) {
        this.code = message.getCode();
        this.message = message.getMessage();
    }
}
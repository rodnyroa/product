package com.example.product.core.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ExceptionBase extends RuntimeException {

    private final String code;

    public ExceptionBase(String message, String code) {
        super(message);
        this.code = code;
    }
}

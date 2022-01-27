package com.example.product.config.controller;

import com.example.product.core.exception.ExceptionBase;
import com.example.product.core.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.persistence.EntityExistsException;

@Slf4j
@ControllerAdvice
@Order(1)
public class BaseControllerException {


    public BaseControllerException() {

    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Void> handleNotFoundException() {
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(headers, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResource> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        FieldError fieldError = e.getBindingResult().getFieldError();
        String element;
        String msg;
        if (fieldError != null) {
            element = e.getBindingResult().getFieldError().getField();
            msg = e.getBindingResult().getFieldError().getDefaultMessage() + ": Field[" + element + "]";
        } else {
            element = e.getBindingResult().getGlobalError().getObjectName();
            msg = e.getBindingResult().getGlobalError().getDefaultMessage() + ": Object[" + element + "]";
        }
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(new ErrorResource("--", msg), headers, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResource> handleExceptionMalformed(Exception e) {
        log.error("HttpMessageNotReadableException:\n{}\n{}", e.getMessage(), e);
        ErrorResource error = new ErrorResource(Messages.BAD_REQUEST);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(error);
    }

    @ExceptionHandler(EntityExistsException.class)
    public ResponseEntity<ErrorResource> handleExceptionConflict(Exception e) {
        log.error("EntityExistsException:\n{}\n{}", e.getMessage(), e);
        ErrorResource error = new ErrorResource(Messages.ENTITY_ALREADY_EXISTS);
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(error);
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<ErrorResource> handleElementNotFound(Exception e) {
        log.info("EmptyResultDataAccessException:\n{}\n{}", e.getMessage(), e);
        ErrorResource error = new ErrorResource(Messages.NOT_FOUND);
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(error);
    }

    @ExceptionHandler(ExceptionBase.class)
    public ResponseEntity<ErrorResource> handleBaseException(ExceptionBase e) {
        log.error("ExceptionBase:\n{}\n{}", e.getMessage(), e);
        ErrorResource error = new ErrorResource(e.getCode(), e.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(error);
    }

    @ExceptionHandler(PropertyReferenceException.class)
    public ResponseEntity<ErrorResource> handleMethodPropertyReferenceException(PropertyReferenceException e) {
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(new ErrorResource("--", e.getMessage()), headers, HttpStatus.BAD_REQUEST);
    }

}

package com.example.carrier_company.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String className, Long id) {
        super(className + " with ID " + id + " was not found!");
    }
}
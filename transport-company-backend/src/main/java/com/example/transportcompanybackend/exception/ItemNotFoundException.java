package com.example.transportcompanybackend.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static java.util.Optional.of;


@ResponseStatus(value = NOT_FOUND)
public class ItemNotFoundException extends RuntimeException {
    public static final String DEFAULT_MESSAGE = "Entity not found";

    public ItemNotFoundException() {
        super(DEFAULT_MESSAGE);
    }

    public ItemNotFoundException(String message) {
        super(message);
    }

    public ItemNotFoundException(Class<?> entityClass, String propertyName, Object propertyValue) {
        super(String.format("%s: {%s, %s=%s}", DEFAULT_MESSAGE, entityClass.getSimpleName(), propertyName,
                of(propertyValue).map(Object::toString).orElse("null")));
    }

    public ItemNotFoundException(Class<?> entityClass, Long id) {
        this(entityClass, "id", id);
    }
}
package com.example.transportcompanybackend.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.NOT_IMPLEMENTED;


@ResponseStatus(value = NOT_IMPLEMENTED)
public class NotImplementedException extends RuntimeException {
    public static final String DEFAULT_MESSAGE = "Method not implemented";

    public NotImplementedException() {
        super(DEFAULT_MESSAGE);
    }

    public NotImplementedException(String message) {
        super(message);
    }
}

package com.example.transportcompanybackend.exception;

public class WrongParametersException extends RuntimeException{
    public WrongParametersException(String message) {
        super(message);
    }
}

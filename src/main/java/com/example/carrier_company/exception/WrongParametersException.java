package com.example.carrier_company.exception;

public class WrongParametersException extends RuntimeException{
    public WrongParametersException(String message) {
        super(message);
    }
}

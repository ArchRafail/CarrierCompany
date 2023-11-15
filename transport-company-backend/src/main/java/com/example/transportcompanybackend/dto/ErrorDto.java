package com.example.transportcompanybackend.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.sql.Timestamp;
import java.time.Instant;


@NoArgsConstructor
@Data
public class ErrorDto {
    private Timestamp timestamp = Timestamp.from(Instant.now());
    private int status;
    private String message;

    public ErrorDto(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public static ErrorDto of(HttpStatus httpStatus, Exception exception) {
        String exceptionMessage = exception.getMessage();
        return new ErrorDto(httpStatus.value(), exceptionMessage == null || exceptionMessage.isBlank() ? httpStatus.getReasonPhrase() : exceptionMessage);
    }

    public static ErrorDto of(HttpStatus httpStatus, String message) {
        return new ErrorDto(httpStatus.value(), message);
    }
}


package com.example.transportcompanybackend.exception.handler;

import com.example.transportcompanybackend.dto.ErrorDto;
import com.example.transportcompanybackend.exception.ItemNotFoundException;
import com.example.transportcompanybackend.exception.NotImplementedException;
import com.example.transportcompanybackend.exception.WrongParametersException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.xml.bind.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MissingRequestCookieException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
@Slf4j
public class RestControllerAdvisor {
    private static final String DEFAULT_SECURITY_EXCEPTION_MESSAGE = "Invalid username or password";

    private static String exceptionDescription(Exception exception) {
        return exception.getClass().getName().concat(": ").concat(exception.getMessage());
    }

    @ExceptionHandler(ItemNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDto handleNotFoundException(ItemNotFoundException exception) {
        return ErrorDto.of(HttpStatus.NOT_FOUND, exception);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorDto handleUsernameNotFoundException(UsernameNotFoundException exception) {
        log.debug(exceptionDescription(exception));
        return ErrorDto.of(HttpStatus.UNAUTHORIZED, DEFAULT_SECURITY_EXCEPTION_MESSAGE);
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorDto handleBadCredentialsException() {
        return ErrorDto.of(HttpStatus.UNAUTHORIZED, DEFAULT_SECURITY_EXCEPTION_MESSAGE);
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorDto handleAuthenticationException(AuthenticationException exception) {
        log.error(exceptionDescription(exception));
        return ErrorDto.of(HttpStatus.UNAUTHORIZED, DEFAULT_SECURITY_EXCEPTION_MESSAGE);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorDto handleExpiredJwtException() {
        return ErrorDto.of(HttpStatus.UNAUTHORIZED, "Token is expired");
    }

    @ExceptionHandler(JwtException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorDto handleJwtException(JwtException exception) {
        log.error(exceptionDescription(exception));
        return ErrorDto.of(HttpStatus.UNAUTHORIZED, DEFAULT_SECURITY_EXCEPTION_MESSAGE);
    }

    @ExceptionHandler(MissingRequestCookieException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorDto handleMissingRequestCookieException() {
        return ErrorDto.of(HttpStatus.UNAUTHORIZED, "Missing required cookies");
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorDto handleHttpConverterException(HttpMessageNotReadableException exception) {
        log.error(exceptionDescription(exception));
        return ErrorDto.of(HttpStatus.UNAUTHORIZED, DEFAULT_SECURITY_EXCEPTION_MESSAGE);
    }

    @ExceptionHandler(WrongParametersException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDto handleWrongParametersException(WrongParametersException exception) {
        return ErrorDto.of(HttpStatus.BAD_REQUEST, exception);
    }

    @ExceptionHandler({DataAccessException.class, ValidationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDto handleDataException(Exception exception) {
        log.error(exceptionDescription(exception));
        return ErrorDto.of(HttpStatus.BAD_REQUEST, exception);
    }

    @ExceptionHandler(NotImplementedException.class)
    @ResponseStatus(HttpStatus.NOT_IMPLEMENTED)
    public ErrorDto handleNotImplementedException(NotImplementedException exception) {
        log.error(exceptionDescription(exception));
        return ErrorDto.of(HttpStatus.NOT_IMPLEMENTED, exception);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorDto handleUnknownException(Exception exception) {
        log.error("Unhandled exception", exception);
        return ErrorDto.of(HttpStatus.INTERNAL_SERVER_ERROR, exception);
    }
}

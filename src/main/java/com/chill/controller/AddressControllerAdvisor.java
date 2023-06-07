package com.chill.controller;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class AddressControllerAdvisor {

    /**
     * This exception handler method handles {@code MethodArgumentNotValidException} which is
     * thrown when a method argument annotated with {@code @Valid} fails validation.
     * <p>
     * When the exception is caught, a response with an HTTP status of BAD_REQUEST (400) is returned,
     * and the response body contains a map of field names to error messages indicating what went wrong.
     *
     * @param ex The exception that was thrown due to invalid method arguments.
     * @return a map of field names to error messages indicating what went wrong.
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult()
                .getAllErrors()
                .forEach(
                        error -> {
                            String fieldName = ((FieldError) error).getField();
                            String errorMessage = error.getDefaultMessage();
                            errors.put(fieldName, errorMessage);
                        });
        return errors;
    }
}

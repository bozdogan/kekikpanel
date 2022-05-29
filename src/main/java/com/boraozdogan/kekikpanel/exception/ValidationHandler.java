package com.boraozdogan.kekikpanel.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;

@ControllerAdvice
public class ValidationHandler
        extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {

        var result = new HashMap<String, String>();
        ex.getBindingResult().getAllErrors().forEach(e -> {
            result.put(
                    ((FieldError) e).getField(),
                    e.getDefaultMessage()
            );
        });

        return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    }
}

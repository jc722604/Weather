package com.example.details.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class MyControllerAdvice extends ResponseEntityExceptionHandler {



    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handleEmpty (NotFoundException notFoundException) {
        return new ResponseEntity<String>("This is empty!", HttpStatus.BAD_REQUEST);
    }



}

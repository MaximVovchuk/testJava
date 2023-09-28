package com.teamvoy.testJava.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionsHandler {
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<Object> handleAlreadyExistException(CustomException ex) {
        return ResponseEntity.status(ex.code).body(ex.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity.status(400).body("Something went wrong : " + ex.getMessage());
    }
}

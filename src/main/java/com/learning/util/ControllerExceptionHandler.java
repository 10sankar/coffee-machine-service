package com.learning.util;

import com.learning.exception.BeverageNotFoundException;
import com.learning.exception.IngredientNotFoundException;
import com.learning.exception.InsufficientIngredientException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(BeverageNotFoundException.class)
    public ResponseEntity<String> handleBeverageNotFoundException(BeverageNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Unknown item : " + ex.getMessage());
    }

    @ExceptionHandler(IngredientNotFoundException.class)
    public ResponseEntity<String> handleIngredientNotFoundException(IngredientNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Unknown item : " + ex.getMessage());
    }

    @ExceptionHandler(InsufficientIngredientException.class)
    public ResponseEntity<String> handleInsufficientIngredientException(InsufficientIngredientException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).
                body("Insufficient ingredient: " + ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(InsufficientIngredientException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
    }
}

package com.learning.exception;

public class InsufficientIngredientException extends RuntimeException {
    public InsufficientIngredientException(String message) {
        super(message);
    }
}

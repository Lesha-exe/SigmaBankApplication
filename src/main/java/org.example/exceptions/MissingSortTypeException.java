package org.example.exceptions;

public class MissingSortTypeException extends RuntimeException {
    public MissingSortTypeException(String message) {
        super(message);
    }
}

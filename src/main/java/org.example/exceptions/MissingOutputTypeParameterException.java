package org.example.exceptions;

public class MissingOutputTypeParameterException extends RuntimeException {
    public MissingOutputTypeParameterException(String message) {
        super(message);
    }
}

package org.example.exceptions;

public class MissingStringArgumentsException extends RuntimeException {
    public MissingStringArgumentsException(String message) {
        super(message);
    }
}

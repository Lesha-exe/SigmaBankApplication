package org.example.exceptionHandler;

public class MissingFilePathException extends RuntimeException{
    public MissingFilePathException(String message) {
    super(message);
    }
}

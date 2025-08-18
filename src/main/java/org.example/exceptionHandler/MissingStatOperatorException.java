package org.example.exceptionHandler;

public class MissingStatOperatorException extends RuntimeException{
    public MissingStatOperatorException(String message){
        super(message);
    }
}

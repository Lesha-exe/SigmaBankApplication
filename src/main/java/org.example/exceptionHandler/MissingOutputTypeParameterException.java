package org.example.exceptionHandler;

public class MissingOutputTypeParameterException extends RuntimeException{
    public MissingOutputTypeParameterException(String message){
        super(message);
    }
}

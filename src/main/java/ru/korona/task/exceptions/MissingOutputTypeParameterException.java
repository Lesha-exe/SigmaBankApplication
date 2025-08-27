package ru.korona.task.exceptions;

public class MissingOutputTypeParameterException extends RuntimeException {
    public MissingOutputTypeParameterException(String message) {
        super(message);
    }
}

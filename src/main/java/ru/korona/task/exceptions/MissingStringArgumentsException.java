package ru.korona.task.exceptions;

public class MissingStringArgumentsException extends RuntimeException {
    public MissingStringArgumentsException(String message) {
        super(message);
    }
}

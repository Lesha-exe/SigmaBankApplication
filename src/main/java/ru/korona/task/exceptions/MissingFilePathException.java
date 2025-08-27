package ru.korona.task.exceptions;

public class MissingFilePathException extends RuntimeException {
    public MissingFilePathException(String message) {
        super(message);
    }
}

package ru.korona.task.exceptions;

public class MissingSortTypeException extends RuntimeException {
    public MissingSortTypeException(String message) {
        super(message);
    }
}

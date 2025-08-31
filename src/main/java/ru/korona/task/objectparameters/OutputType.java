package ru.korona.task.objectparameters;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum OutputType {
    FILE("file"),
    CONSOLE("console");
    private final String name;

    public static OutputType from(String name) {
        for (OutputType type : values()) {
            if (type.getName().equals(name)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Incorrect output type parameter: " + name);
    }
}

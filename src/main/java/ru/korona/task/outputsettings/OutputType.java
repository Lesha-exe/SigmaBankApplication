package ru.korona.task.outputsettings;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum OutputType {
    FILE("FILE"),
    CONSOLE("CONSOLE");
    private final String name;

    public static OutputType from(String name) {
        for (OutputType type : values()) {
            if (type.getName().equals(name)) {
                return type;
            }
        }
        throw new IllegalArgumentException("ERROR! Incorrect output type parameter: " + name);
    }
}

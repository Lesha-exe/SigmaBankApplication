package ru.korona.task.outputsettings;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SortType {
    NAME("NAME"),
    SALARY("SALARY");
    private final String name;

    public static SortType from(String name) {
        for (SortType type : values()) {
            if (type.getName().equals(name)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Incorrect sort type parameter: " + name);
    }
}

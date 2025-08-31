package ru.korona.task.objectparameters;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SortType {
    NAME("name"),
    SALARY("salary");
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

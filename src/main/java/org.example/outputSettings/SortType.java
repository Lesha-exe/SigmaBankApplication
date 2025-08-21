package org.example.outputSettings;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SortType {
    NAME("NAME"),
    SALARY("SALARY"),
    UNDEFINED("");
    private final String name;

    public static SortType from(String name) {
        for (SortType type : values()) {
            if (type.getName().equals(name)) {
                return type;
            }
        }
        throw new IllegalArgumentException("ERROR! Incorrect sort type parameter: " + name);
    }
}

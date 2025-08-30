package ru.korona.task.outputsettings;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum OrderType {
    ASC("ASC"),
    DESC("DESC");
    private final String name;

    public static OrderType from(String name) {
        for (OrderType type : values()) {
            if (type.getName().equals(name)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Incorrect order type parameter: " + name);
    }
}

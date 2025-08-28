package ru.korona.task.outputsettings;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum WorkerType {
    MANAGER("Manager"),
    EMPLOYEE("Employee");
    private final String name;

    public static WorkerType from(String name) {
        for (WorkerType type : values()) {
            if (type.getName().equals(name)) {
                return type;
            }
        }
        throw new IllegalArgumentException("ERROR! Incorrect worker type parameter: " + name);
    }
}

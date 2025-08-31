package ru.korona.task.objectparameters;

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
        throw new IllegalArgumentException("Incorrect worker type parameter: " + name);
    }
}

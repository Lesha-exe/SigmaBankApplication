package ru.korona.task.outputsettings;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum StatisticsType {
    MAX_SALARY("max"),
    MIN_SALARY("min"),
    MID_SALARY("mid");
    private final String name;
    public static StatisticsType from(String name){
        for (StatisticsType type : values()) {
            if (type.getName().equals(name)) {
                return type;
            }
        }
        throw new IllegalArgumentException("ERROR! Incorrect statistics type parameter: " + name);
    }
}

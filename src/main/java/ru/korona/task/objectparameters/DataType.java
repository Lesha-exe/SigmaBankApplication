package ru.korona.task.objectparameters;

import java.util.List;
import ru.korona.task.models.Department;
import ru.korona.task.models.DepartmentStatistics;

public enum DataType {
    DEPARTMENT,
    STATISTICS,
    INVALID_DATA;

    public static DataType from(List<Object> data) {
        if (data.get(0) instanceof Department) {
            return DEPARTMENT;
        } else if (data.get(0) instanceof DepartmentStatistics) {
            return STATISTICS;
        } else {
            return INVALID_DATA;
        }
    }
}

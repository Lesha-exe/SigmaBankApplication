package ru.korona.task.service.statistics;

import ru.korona.task.models.Department;
import ru.korona.task.objectparameters.StatisticsType;

public interface StatisticsCalculator {
    double calculate(Department department);

    StatisticsType getType();
}

package ru.korona.task.services;

import ru.korona.task.models.Department;
import ru.korona.task.outputsettings.StatisticsType;

public interface StatisticsCalculator {
    double calculate(Department department);

    StatisticsType getType();
}

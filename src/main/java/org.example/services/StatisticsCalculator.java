package org.example.services;

import org.example.models.Department;
import org.example.outputSettings.StatisticsType;

public interface StatisticsCalculator {
    double calculate(Department department);

    StatisticsType getType();
}

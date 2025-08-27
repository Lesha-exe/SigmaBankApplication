package ru.korona.task.services;

import ru.korona.task.models.Department;
import ru.korona.task.models.Employee;
import ru.korona.task.outputSettings.StatisticsType;

public class MinSalaryStatisticsCalculator implements StatisticsCalculator {
    @Override
    public double calculate(Department department) {
        return department.getEmployeeList().stream()
                .mapToDouble(Employee::getSalary)
                .min()
                .orElse(0.0);
    }

    @Override
    public StatisticsType getType() {
        return StatisticsType.MIN_SALARY;
    }
}

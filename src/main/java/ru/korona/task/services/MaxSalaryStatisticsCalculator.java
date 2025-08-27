package ru.korona.task.services;

import ru.korona.task.models.Department;
import ru.korona.task.models.Employee;
import ru.korona.task.outputSettings.StatisticsType;

public class MaxSalaryStatisticsCalculator implements StatisticsCalculator {
    @Override
    public double calculate(Department department) {
        return department.getEmployeeList().stream()
                .mapToDouble(Employee::getSalary)
                .max()
                .orElse(0.0);
    }

    @Override
    public StatisticsType getType() {
        return StatisticsType.MAX_SALARY;
    }
}

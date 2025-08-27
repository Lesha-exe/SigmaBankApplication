package ru.korona.task.services;

import ru.korona.task.models.Department;
import ru.korona.task.models.Employee;
import ru.korona.task.outputSettings.StatisticsType;

public class MidSalaryStatisticsCalculator implements StatisticsCalculator {
    @Override
    public double calculate(Department department) {
        return department.getEmployeeList().stream()
                .mapToDouble(Employee::getSalary)
                .average()
                .orElse(0.0);
    }

    @Override
    public StatisticsType getType() {
        return StatisticsType.MID_SALARY;
    }
}

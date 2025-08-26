package org.example.services;

import org.example.models.Department;
import org.example.models.Employee;
import org.example.outputSettings.StatisticsType;

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

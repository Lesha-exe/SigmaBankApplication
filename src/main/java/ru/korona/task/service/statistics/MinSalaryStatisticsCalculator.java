package ru.korona.task.service.statistics;

import org.springframework.stereotype.Component;
import ru.korona.task.models.Department;
import ru.korona.task.models.Employee;
import ru.korona.task.objectparameters.StatisticsType;

@Component
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

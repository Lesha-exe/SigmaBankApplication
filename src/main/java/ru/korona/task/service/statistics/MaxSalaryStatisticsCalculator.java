package ru.korona.task.service.statistics;

import org.springframework.stereotype.Component;
import ru.korona.task.models.Department;
import ru.korona.task.models.Employee;
import ru.korona.task.objectparameters.StatisticsType;

@Component
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

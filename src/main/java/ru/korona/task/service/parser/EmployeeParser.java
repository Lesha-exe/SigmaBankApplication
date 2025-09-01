package ru.korona.task.service.parser;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import ru.korona.task.models.Employee;
import ru.korona.task.models.Worker;
import ru.korona.task.models.WorkerWithIncorrectData;

@Component
public class EmployeeParser implements WorkerParser {
    @Override
    public Worker parse(String[] workerData) {
        Employee.EmployeeBuilder employeeBuilder = Employee.builder();
        try {
            employeeBuilder.id(Integer.parseInt(workerData[1].trim()));
            employeeBuilder.salary(Double.parseDouble(workerData[3].trim()));
            employeeBuilder.managerId(Integer.parseInt(workerData[4].trim()));
        } catch (NumberFormatException exception) {
            return new WorkerWithIncorrectData(workerData);
        }
        employeeBuilder.name(workerData[2].trim());
        Employee employee = employeeBuilder.build();
        if (!isValid(employee)) {
            return new WorkerWithIncorrectData(workerData);
        }
        return employee;
    }

    private boolean isValid(Employee employee) {
        return employee.getId() > 0
                && employee.getSalary() > 0
                && StringUtils.isNotBlank(employee.getName())
                && employee.getManagerId() > 0;
    }
}

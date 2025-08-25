package org.example.services;

import org.example.models.Employee;
import org.example.models.Worker;
import org.example.models.WorkerWithIncorrectData;

public class EmployeeParser implements WorkerParser {
    @Override
    public Worker parse(String[] workerData) {
        Employee.EmployeeBuilder employeeBuilder = Employee.builder();
        try {
            employeeBuilder.id(Integer.parseInt(workerData[1]));
            employeeBuilder.salary(Integer.parseInt(workerData[3]));
            employeeBuilder.managerId(Integer.parseInt(workerData[4]));
        } catch (NumberFormatException exception) {
            return new WorkerWithIncorrectData(workerData);
        }
        employeeBuilder.name(workerData[2]);
        Employee employee = employeeBuilder.build();
        if (!isValid(employee)) {
            return new WorkerWithIncorrectData(workerData);
        }
        return employee;
    }

    private boolean isValid(Employee employee) {
        if (employee.getId() < 0 || employee.getId().equals(null)) {
            return false;
        } else if (employee.getSalary() < 0 || employee.getSalary().equals(null)) {
            return false;
        } else if (employee.getManagerId() < 0 || employee.getManagerId().equals(null)) {
            return false;
        } else if (employee.getName().equals(null)) {
            return false;
        }
        return true;
    }
}

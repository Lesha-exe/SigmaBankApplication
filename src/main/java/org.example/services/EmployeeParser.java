package org.example.services;

import lombok.Builder;
import org.example.models.Employee;
import org.example.models.Worker;

public class EmployeeParser implements WorkerParser{
    @Override
    public Worker parse(String[] workerData) {
        Employee.EmployeeBuilder employeeBuilder = Employee.builder();
        employeeBuilder.id(Integer.parseInt(workerData[1]));
        employeeBuilder.name(workerData[2]);
        employeeBuilder.salary(Integer.parseInt(workerData[3]));
        employeeBuilder.managerId(Integer.parseInt(workerData[4]));
        return employeeBuilder.build();
    }
}

package org.example.services;

import lombok.Builder;
import org.example.models.Employee;
import org.example.models.Worker;

public class EmployeeParser implements WorkerParser{
    @Override
    public Worker parse(String[] workerData) {

        return Employee.builder();
    }
}

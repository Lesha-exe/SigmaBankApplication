package org.example.services;

import org.example.outputSettings.WorkerType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class Config {
    @Bean
    public WorkerDataParser workerDataParser(ManagerParser managerParser, EmployeeParser employeeParser) {
        return new WorkerDataParser(Map.of(WorkerType.MANAGER, managerParser, WorkerType.EMPLOYEE, employeeParser));
    }
}

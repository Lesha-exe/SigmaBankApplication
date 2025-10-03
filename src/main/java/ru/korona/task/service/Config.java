package ru.korona.task.service;

import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.korona.task.objectparameters.WorkerType;
import ru.korona.task.service.parser.EmployeeParser;
import ru.korona.task.service.parser.ManagerParser;
import ru.korona.task.service.parser.WorkerDataParser;

@Configuration
public class Config {
    @Bean
    public WorkerDataParser workerDataParser(
            ManagerParser managerParser, EmployeeParser employeeParser) {
        return new WorkerDataParser(
                Map.of(WorkerType.MANAGER, managerParser, WorkerType.EMPLOYEE, employeeParser));
    }
}


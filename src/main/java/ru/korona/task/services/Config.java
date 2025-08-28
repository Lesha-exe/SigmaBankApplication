package ru.korona.task.services;

import java.util.Map;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.korona.task.outputsettings.WorkerType;

@Configuration
public class Config {
    @Bean
    public WorkerDataParser workerDataParser(
            ManagerParser managerParser, EmployeeParser employeeParser) {
        return new WorkerDataParser(
                Map.of(WorkerType.MANAGER, managerParser, WorkerType.EMPLOYEE, employeeParser));
    }
}

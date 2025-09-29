package ru.korona.task.service;

import java.util.Map;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.korona.task.objectparameters.DataType;
import ru.korona.task.objectparameters.WorkerType;
import ru.korona.task.service.parser.EmployeeParser;
import ru.korona.task.service.parser.ManagerParser;
import ru.korona.task.service.parser.WorkerDataParser;
import ru.korona.task.service.storagedata.DepartmentStorage;
import ru.korona.task.service.storagedata.FileService;
import ru.korona.task.service.storagedata.InvalidDataStorage;
import ru.korona.task.service.storagedata.StatisticsStorage;

@Configuration
public class Config {
    @Bean
    public WorkerDataParser workerDataParser(
            ManagerParser managerParser, EmployeeParser employeeParser) {
        return new WorkerDataParser(
                Map.of(WorkerType.MANAGER, managerParser, WorkerType.EMPLOYEE, employeeParser));
    }

    @Bean
    public FileService fileService(
            DepartmentStorage departmentStorage,
            StatisticsStorage statisticsStorage,
            InvalidDataStorage invalidDataStorage) {
        return new FileService(
                Map.of(
                        DataType.DEPARTMENT,
                        departmentStorage,
                        DataType.STATISTICS,
                        statisticsStorage,
                        DataType.INVALIDData,
                        invalidDataStorage));
    }
}

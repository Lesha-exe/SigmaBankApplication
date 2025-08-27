package ru.korona.task;

import java.util.List;

import lombok.RequiredArgsConstructor;
import ru.korona.task.models.AppArguments;
import ru.korona.task.models.Department;
import ru.korona.task.services.*;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WorkerDataApplication {
    private final ArgumentsReader argumentsReader;
    private final WorkerDataReader workerDataReader;
    private final DepartmentService departmentServices;
    private final DepartmentStatisticsService departmentStatisticsService;

    public void run(String[] args) {
        try {
            AppArguments appArguments = argumentsReader.readArguments(args);
            WorkerData workerData = workerDataReader.readWorkers();
            List<Department> departments =
                    departmentServices.createDepartments(
                            workerData.getWorkersWithCorrectData(), appArguments);
            departmentServices.storeDepartments(departments);
            departmentStatisticsService.calculateStatistics(departments, appArguments);
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }
}

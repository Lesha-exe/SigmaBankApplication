package ru.korona.task;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.korona.task.models.AppArguments;
import ru.korona.task.models.Department;
import ru.korona.task.models.DepartmentStatistics;
import ru.korona.task.service.*;
//import ru.korona.task.service.reader.ArgumentsReader;
import ru.korona.task.service.reader.ArgumentsReader;
import ru.korona.task.service.reader.WorkerData;
import ru.korona.task.service.reader.WorkerDataReader;
import ru.korona.task.service.statistics.DepartmentStatisticsService;
import ru.korona.task.service.statistics.StatisticsDataStorage;

@Component
@RequiredArgsConstructor
public class WorkerDataApplication {
    private final ArgumentsReader argumentsReader;
    private final WorkerDataReader workerDataReader;
    private final DepartmentService departmentServices;
    private final DepartmentStatisticsService departmentStatisticsService;
    private final InvalidDataService invalidDataService;
    private final StatisticsDataStorage statisticsDataStorage;

    public void run(String[] args) {
        try {
            AppArguments appArguments = argumentsReader.readArguments(args);
            WorkerData workerData = workerDataReader.readWorkers();
            List<Department> departments =
                    departmentServices.createDepartments(
                            workerData.getWorkersWithCorrectData(), appArguments);
            departmentServices.storeDepartments(departments);
            invalidDataService.storeInvalidData(workerData.getWorkersWithIncorrectData());
            if (!appArguments.getStatisticsConfig().isStatisticsPresent()) {
                return;
            }
            List<DepartmentStatistics> departmentStatistics =
                    departmentStatisticsService.calculateStatistics(departments);
            statisticsDataStorage.storeStatistics(departmentStatistics, appArguments);
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }
}

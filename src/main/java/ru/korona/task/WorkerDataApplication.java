package ru.korona.task;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.korona.task.models.AppArguments;
import ru.korona.task.models.Department;
import ru.korona.task.models.DepartmentStatistics;
import ru.korona.task.services.*;

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
            if (!appArguments.getStatisticsConfig().getIsStatisticsPresent()) {
                return;
            }
            List<DepartmentStatistics> departmentStatistics =
                    departmentStatisticsService.calculateStatistics(departments);
            if ((appArguments.getStatisticsConfig().getIsStatisticsPresent())
                    && (appArguments.getStatisticsConfig().getOutputFilePath() != null)) {
                statisticsDataStorage.storeStatisticsToFile(departmentStatistics, appArguments);
            } else {
                statisticsDataStorage.storeStatisticsToConsole(departmentStatistics, appArguments);
            }
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }
}

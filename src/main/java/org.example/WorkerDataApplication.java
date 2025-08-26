package org.example;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.models.AppArguments;
import org.example.models.Department;
import org.example.services.ArgumentsReader;
import org.example.services.DepartmentService;
import org.example.services.WorkerData;
import org.example.services.WorkerDataReader;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WorkerDataApplication {
    private final ArgumentsReader argumentsReader;
    private final WorkerDataReader workerDataReader;
    private final DepartmentService departmentServices;

    public void run(String[] args) {
        try {
            AppArguments appArguments = argumentsReader.readArguments(args);
            WorkerData workerData = workerDataReader.readWorkers();
            List<Department> departments =
                    departmentServices.createDepartments(
                            workerData.getWorkersWithCorrectData(), appArguments);

        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }
}

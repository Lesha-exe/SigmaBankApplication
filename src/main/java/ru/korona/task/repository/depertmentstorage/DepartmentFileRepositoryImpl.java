package ru.korona.task.repository.depertmentstorage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import ru.korona.task.models.Department;
import ru.korona.task.models.Employee;
import ru.korona.task.models.Manager;
import ru.korona.task.repository.DepartmentRepository;
import ru.korona.task.service.FileService;

import java.util.List;
import java.util.stream.Stream;

@Component
@Profile("file")
public class DepartmentFileRepositoryImpl implements DepartmentRepository {
    private final FileService fileService;
    private final String outputDirectory;
    private final String outputFileExtensions;

    public DepartmentFileRepositoryImpl(
            @Value("${departments.outputDir}") String outputDirectory,
            @Value("${departments.outputFileExtensions}") String outputFileExtensions,
            FileService fileService) {
        this.fileService = fileService;
        this.outputDirectory = outputDirectory;
        this.outputFileExtensions = outputFileExtensions;
    }

    @Override
    public void storeData(List<Department> departments) {
        departments.forEach(
                department -> {
                    String departmentName =
                            department.getManager().getDepartment() + outputFileExtensions;
                    List<String> workerData =
                            Stream.concat(
                                            Stream.of(createManagerLine(department.getManager())),
                                            department.getEmployeeList().stream()
                                                    .map(DepartmentFileRepositoryImpl::createEmployeeLine))
                                    .toList();
                    fileService.storeData(workerData, outputDirectory, departmentName);
                });
    }

    private static String createManagerLine(Manager manager) {
        return String.format(
                "Manager, %d, %s, %.2f", manager.getId(), manager.getName(), manager.getSalary());
    }

    private static String createEmployeeLine(Employee employee) {
        return String.format(
                "Employee, %d, %s, %.2f, %d",
                employee.getId(),
                employee.getName(),
                employee.getSalary(),
                employee.getManagerId());
    }
}

package ru.korona.task.service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.korona.task.models.*;
import ru.korona.task.objectparameters.OrderType;
import ru.korona.task.objectparameters.SortType;

@Component
@Slf4j
public class DepartmentService {
    private static final Comparator<Employee> SALARY_COMPARATOR_ASC =
            Comparator.comparing(Employee::getSalary);
    private static final Comparator<Employee> SALARY_COMPARATOR_DESC =
            SALARY_COMPARATOR_ASC.reversed();
    private static final Comparator<Employee> NAME_COMPARATOR_ASC =
            Comparator.comparing(Employee::getName);
    private static final Comparator<Employee> NAME_COMPARATOR_DESC = NAME_COMPARATOR_ASC.reversed();
    private final FileService fileService;
    private final String outputDirectory;
    private final String outputFileExtensions;

    public DepartmentService(
            @Value("${departments.outputDir}") String outputDirectory,
            @Value("${departments.outputFileExtensions}") String outputFileExtensions,
            FileService fileService) {
        this.fileService = fileService;
        this.outputDirectory = outputDirectory;
        this.outputFileExtensions = outputFileExtensions;
    }

    public List<Department> createDepartments(List<Worker> workers, AppArguments appArguments) {
        final List<Manager> managers = workersOfType(workers, Manager.class);
        final List<Employee> employees = workersOfType(workers, Employee.class);
        Map<Integer, List<Employee>> employeeMap =
                employees.stream().collect(Collectors.groupingBy(Employee::getManagerId));
        return managers.stream()
                .map(manager -> createDepartment(appArguments, manager, employeeMap))
                .toList();
    }

    private static <T extends Worker> List<T> workersOfType(List<Worker> workers, Class<T> type) {
        return workers.stream()
                .filter(worker -> type.isInstance(worker))
                .map(worker -> type.cast(worker))
                .toList();
    }

    private Department createDepartment(
            AppArguments appArguments, Manager manager, Map<Integer, List<Employee>> employeeMap) {
        final List<Employee> departmentEmployees = employeeMap.get(manager.getId());
        sortEmployees(departmentEmployees, appArguments.getSortType(), appArguments.getOrder());
        return new Department(manager, departmentEmployees);
    }

    private void sortEmployees(
            List<Employee> departmentEmployees, SortType sortType, OrderType orderType) {
        if (sortType == null) {
            return;
        }
        departmentEmployees.sort(getEmployeesComparator(sortType, orderType));
    }

    private Comparator<Employee> getEmployeesComparator(SortType sortType, OrderType orderType) {
        if (sortType == SortType.SALARY) {
            return orderType == OrderType.ASC ? SALARY_COMPARATOR_ASC : SALARY_COMPARATOR_DESC;
        } else {
            return orderType == OrderType.ASC ? NAME_COMPARATOR_ASC : NAME_COMPARATOR_DESC;
        }
    }

    public void storeDepartments(List<Department> departments) {
        departments.forEach(
                department -> {
                    String departmentName =
                            department.getManager().getDepartment() + outputFileExtensions;
                    List<String> workerData =
                            Stream.concat(
                                            Stream.of(createManagerLine(department.getManager())),
                                            department.getEmployeeList().stream()
                                                    .map(DepartmentService::createEmployeeLine))
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

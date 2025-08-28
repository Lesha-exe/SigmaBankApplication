package ru.korona.task.services;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.korona.task.models.*;
import ru.korona.task.outputsettings.OrderType;
import ru.korona.task.outputsettings.SortType;

@Component
public class DepartmentService {
    private static final Comparator<Employee> SALARY_COMPARATOR_ASC =
            Comparator.comparing(Employee::getSalary);
    private static final Comparator<Employee> SALARY_COMPARATOR_DESC =
            SALARY_COMPARATOR_ASC.reversed();
    private static final Comparator<Employee> NAME_COMPARATOR_ASC =
            Comparator.comparing(Employee::getName);
    private static final Comparator<Employee> NAME_COMPARATOR_DESC = NAME_COMPARATOR_ASC.reversed();

    private String outputDirectory;

    public DepartmentService(@Value("${departments.outputDir}") String outputDirectory) {
        this.outputDirectory = outputDirectory;
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
                    String departmentName = department.getManager().getDepartment();
                    Path path = Path.of(outputDirectory, departmentName + ".sb");
                    try (BufferedWriter writer = Files.newBufferedWriter(path)) {
                        Files.createDirectories(path.getParent());
                        writeManagerToFile(department.getManager(), writer);
                        for (Employee employee : department.getEmployeeList()) {
                            writeEmployeeToFile(employee, writer);
                        }
                    } catch (Exception exception) {
                        System.out.println("Error while saving department " + departmentName);
                    }
                });
    }

    private static void writeManagerToFile(Manager manager, BufferedWriter writer) {
        writeWorkerToFile(
                String.format(
                        "Manager, %d, %s, %d",
                        manager.getId(), manager.getName(), manager.getSalary()),
                writer);
    }

    private static void writeEmployeeToFile(Employee employee, BufferedWriter writer) {
        writeWorkerToFile(
                String.format(
                        "Employee, %d, %s, %d, %d",
                        employee.getId(),
                        employee.getName(),
                        employee.getSalary(),
                        employee.getManagerId()),
                writer);
    }

    private static void writeWorkerToFile(String workerData, BufferedWriter writer) {
        try {
            writer.write(workerData);
            writer.newLine();
        } catch (Exception exception) {
            System.out.println("Error while writing file: " + exception.getMessage());
        }
    }
}

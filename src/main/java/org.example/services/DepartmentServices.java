package org.example.services;

import org.example.models.*;
import org.example.outputSettings.OrderType;
import org.example.outputSettings.SortType;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DepartmentServices {
    private static final Comparator<Employee> SALARY_COMPARATOR_ASC = Comparator.comparing(Employee::getSalary);
    private static final Comparator<Employee> SALARY_COMPARATOR_DESC = SALARY_COMPARATOR_ASC.reversed();
    private static final Comparator<Employee> NAME_COMPARATOR_ASC = Comparator.comparing(Employee::getName);
    private static final Comparator<Employee> NAME_COMPARATOR_DESC = NAME_COMPARATOR_ASC.reversed();

    public List<Department> createDepartments(List<Worker> workers, AppArguments appArguments) {
        final List<Manager> managers = workersOfType(workers, Manager.class);
        final List<Employee> employees = workersOfType(workers, Employee.class);

        Map<Integer, List<Employee>> employeeMap = employees.stream()
                .collect(Collectors.groupingBy(Employee::getManagerId));
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

    private Department createDepartment(AppArguments appArguments, Manager manager, Map<Integer, List<Employee>> employeeMap) {
        final List<Employee> departmentEmployees = employeeMap.get(manager.getId());
        sortEmployees(departmentEmployees, appArguments.getSortType(), appArguments.getOrder());
        return new Department(manager, departmentEmployees);
    }

    private void sortEmployees(List<Employee> departmentEmployees, SortType sortType, OrderType orderType) {
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

    public void saveDepartments(List<Department> departments) {
        Map<String, Department> departmentMap = departments.stream().
                collect(Collectors.toMap(
                        department -> department.getManager().getDepartment(),
                        department -> department
                ));
        departmentMap.forEach((departmentName, department)-> {
            Path path = Path.of("output", departmentName + ".sb");
            try {
                Files.createDirectories(path.getParent());
                try (BufferedWriter writer = Files.newBufferedWriter(path)){
                    Manager manager = department.getManager();
                    writer.write(String.format(
                            "Manager, %d, %s, %d",
                            manager.getId(),
                            manager.getName(),
                            manager.getSalary()
                    ));
                    writer.newLine();
                    for(Employee employee: department.getEmployeeList()){
                        writer.write(String.format(
                                "Employee, %d, %s, %d, %d",
                                employee.getId(),
                                employee.getName(),
                                employee.getSalary(),
                                employee.getManagerId()
                        ));
                        writer.newLine();
                    }
                }
            } catch (Exception exception){
                throw new RuntimeException("Error while saving department " + exception.getMessage());
            }
        });
    }
}

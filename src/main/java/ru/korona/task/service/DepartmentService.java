package ru.korona.task.service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.korona.task.models.*;
import ru.korona.task.objectparameters.OrderType;
import ru.korona.task.objectparameters.SortType;
import ru.korona.task.repository.DepartmentRepository;
import ru.korona.task.repository.depertmentstorage.DepartmentFileRepositoryImpl;

@Component
@Slf4j
@RequiredArgsConstructor
public class DepartmentService {
    private static final Comparator<Employee> SALARY_COMPARATOR_ASC =
            Comparator.comparing(Employee::getSalary);
    private static final Comparator<Employee> SALARY_COMPARATOR_DESC =
            SALARY_COMPARATOR_ASC.reversed();
    private static final Comparator<Employee> NAME_COMPARATOR_ASC =
            Comparator.comparing(Employee::getName);
    private static final Comparator<Employee> NAME_COMPARATOR_DESC = NAME_COMPARATOR_ASC.reversed();
    private final DepartmentRepository departmentRepository;

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
        if (sortType == null || departmentEmployees == null) {
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
        departmentRepository.storeData(departments);
    }
}

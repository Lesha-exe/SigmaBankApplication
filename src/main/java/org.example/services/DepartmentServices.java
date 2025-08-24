package org.example.services;

import org.example.models.*;
import org.example.outputSettings.OrderType;
import org.example.outputSettings.SortType;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DepartmentServices {
    public List<Department> createDepartment (List<Worker> workers, AppArguments appArguments){
        List<Manager> managers = workers.stream().
                filter(worker -> worker instanceof Manager).
                map(worker -> (Manager) worker).
                toList();
        List<Employee> employees = workers.stream().
                filter(worker -> worker instanceof Employee).
                map(worker -> (Employee) worker).
                toList();
        sortEmployees(employees, appArguments.getSortType(), appArguments.getOrder());
        Map<Integer, Manager> managerMap = managers.stream().
                collect(Collectors.toMap(Manager::getId, manager -> manager));
        Map<Integer, List<Employee>> employeeMap = employees.stream().
                collect(Collectors.groupingBy(Employee::getManagerId));

        List<Department> departments =
        return null;
    }

    public void saveDepartments(List<Department> departments){
    }

    private List<Employee> sortEmployees(List<Employee> employees,
                                                       SortType sortType, OrderType orderType){
        if(sortType.equals(SortType.SALARY)){
            return employees.stream().sorted(Comparator.comparing(Employee::getSalary)).toList();
        }
        return employees;
    }

}

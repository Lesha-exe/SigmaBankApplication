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
        Map<Integer, Manager> managerMap = managers.stream().
                collect(Collectors.toMap(Manager::getId, manager -> manager));
        Map<Integer, List<Employee>> employeeMap = employees.stream().
                collect(Collectors.groupingBy(Employee::getManagerId));
        Map<Integer, List<Employee>> employeeSortedMap =
                sortEmployees(employeeMap, appArguments.getSortType(), appArguments.getOrder());
        List<Department> departments = managerMap.values().stream().
                map(manager -> new Department(manager, employeeSortedMap.getOrDefault(manager.getId(), List.of()))).
                toList();
        return departments;
    }

    public void saveDepartments(List<Department> departments){
    }

    private Map<Integer, List<Employee>> sortEmployees(Map<Integer, List<Employee>> employeeMap,
                                                       SortType sortType, OrderType orderType){
        if(sortType.equals(SortType.SALARY) && orderType.equals(OrderType.ASC)){
            return employeeMap.getOrDefault()
            return employeeMap.stream().sorted(Comparator.comparing(Employee::getSalary)).toList();
        } else if (sortType.equals(SortType.SALARY) && orderType.equals(OrderType.DESC)) {
            return employeeMap.stream().sorted(Comparator.comparing(Employee::getSalary).reversed()).toList();
        }
        return employees;
    }

}

package org.example.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
@AllArgsConstructor
@Getter
public class Department {
    private Manager manager;
    private List<Employee> employeeList;
}

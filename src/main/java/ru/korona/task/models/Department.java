package ru.korona.task.models;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Department {
    private Manager manager;
    private List<Employee> employeeList;
}

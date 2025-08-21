package org.example.models;

import lombok.Getter;
import lombok.Setter;

@Getter
public class Employee extends AbstractWorker {
    private Integer managerId;

    public Employee(Integer id, String name, Integer salary, Integer managerId) {
        super(id, name, salary);
        this.managerId = managerId;
    }
}

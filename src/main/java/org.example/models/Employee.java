package org.example.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Employee extends AbstractWorker {
    private Integer managerId;

    public Employee(String post, Integer id, String name, Integer salary, Integer managerId) {
        super(post, id, name, salary);
        this.managerId = managerId;
    }
}

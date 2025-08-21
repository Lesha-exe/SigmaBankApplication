package org.example.models;

import lombok.Getter;
import lombok.Setter;

@Getter
public class Manager extends AbstractWorker {
    private String department;

    public Manager(Integer id, String name, Integer salary, String department) {
        super(id, name, salary);
        this.department = department;
    }
}

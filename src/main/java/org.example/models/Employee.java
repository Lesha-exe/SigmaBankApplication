package org.example.models;

import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Getter
@SuperBuilder
public class Employee extends AbstractWorker {
    private final Integer managerId;

//    public Employee(Integer id, String name, Integer salary, Integer managerId) {
//        super(id, name, salary);
//        this.managerId = managerId;
//    }
}

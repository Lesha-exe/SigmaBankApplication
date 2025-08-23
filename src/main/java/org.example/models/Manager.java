package org.example.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Getter
@SuperBuilder
public class Manager extends AbstractWorker {
    private final String department;

//    public Manager(Integer id, String name, Integer salary, String department) {
//        super(id, name, salary);
//        this.department = department;
//    }
}

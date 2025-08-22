package org.example.models;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Builder
public class Employee extends AbstractWorker {
    private Integer managerId;

    public Employee(Integer id, String name, Integer salary, Integer managerId) {
        super(id, name, salary);
        this.managerId = managerId;
    }
}

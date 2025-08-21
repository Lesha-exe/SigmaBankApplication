package org.example.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public abstract class AbstractWorker implements Worker {
    private Integer id;
    private String name;
    private Integer salary;

}

package org.example.models;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public abstract class AbstractWorker implements Worker {
    private String post;
    private Integer id;
    private String name;
    private Integer salary;

}

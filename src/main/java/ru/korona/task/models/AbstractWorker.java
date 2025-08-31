package ru.korona.task.models;

import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode
@ToString
@Getter
@SuperBuilder
public abstract class AbstractWorker implements Worker {
    private final Integer id;
    private final String name;
    private final Double salary;
}

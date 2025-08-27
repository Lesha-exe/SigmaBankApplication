package ru.korona.task.models;

import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Getter
@SuperBuilder
public class Employee extends AbstractWorker {
    private final Integer managerId;
}

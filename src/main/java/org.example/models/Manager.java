package org.example.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Getter
@SuperBuilder
public class Manager extends AbstractWorker {
    private final String department;
}

package org.example.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public abstract class AbstractWorker implements Worker {
    private Integer id;
    private String name;
    private Integer salary;

}

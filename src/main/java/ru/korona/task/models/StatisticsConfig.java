package ru.korona.task.models;

import lombok.*;
import ru.korona.task.objectparameters.OutputType;

@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Getter
@Setter
public class StatisticsConfig {
    private boolean isStatisticsPresent;
    private OutputType outputType;
    private String outputFilePath;
}

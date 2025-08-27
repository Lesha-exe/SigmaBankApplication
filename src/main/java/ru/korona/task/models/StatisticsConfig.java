package ru.korona.task.models;

import lombok.*;
import ru.korona.task.outputSettings.OutputType;

@RequiredArgsConstructor
@EqualsAndHashCode
@ToString
@Getter
@Builder
public class StatisticsConfig {
    private final Boolean isStatisticsPresent;
    private final OutputType outputType;
    private final String outputFilePath;
}

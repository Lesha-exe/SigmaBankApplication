package org.example.models;

import lombok.*;
import org.example.outputSettings.OutputType;


@RequiredArgsConstructor
@EqualsAndHashCode
@ToString
@Getter
@Builder
public class StatisticsConfig {
    private final Boolean statFlag;
    private final OutputType outputType;
    private final String outputFilePath;
}

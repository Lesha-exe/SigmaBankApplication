package org.example.models;

import lombok.*;
import org.example.outputSettings.OutputType;


@Data
@Builder
public class StatisticsConfig {
    private Boolean statFlag;
    private OutputType outputType;
    private String outputFilePath;
}

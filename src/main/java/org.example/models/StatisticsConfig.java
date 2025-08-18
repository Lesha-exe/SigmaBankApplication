package org.example.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.example.outputSettings.OutputType;


@AllArgsConstructor
@Getter
@Setter
public class StatisticsConfig {
    private Boolean statFlag;
    private OutputType outputType;
    private String outputFilePath;

    public StatisticsConfig(){
        statFlag = false;
    }

//    public boolean getStatFlag(){
//        return statFlag;
//    }
}

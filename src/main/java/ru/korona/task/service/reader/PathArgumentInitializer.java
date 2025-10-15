package ru.korona.task.service.reader;

import ru.korona.task.models.AppArguments;
import ru.korona.task.models.StatisticsConfig;
import ru.korona.task.objectparameters.OutputType;

import java.util.List;

import static ru.korona.task.service.reader.ArgumentKey.*;

public class PathArgumentInitializer implements ArgumentsInitializer{
    @Override
    public void initialize(String argumentValue, AppArguments appArguments) {
        StatisticsConfig statisticsConfig = appArguments.getStatisticsConfig();
        if(statisticsConfig == null){
            statisticsConfig = new StatisticsConfig();
        }
        statisticsConfig.setOutputFilePath(argumentValue);
    }

    @Override
    public List<String> argumentKeys() {
        return List.of(PATH_ARGUMENT_PREFIX);
    }
}

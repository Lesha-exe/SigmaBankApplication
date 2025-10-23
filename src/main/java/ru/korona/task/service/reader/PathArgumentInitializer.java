package ru.korona.task.service.reader;

import static ru.korona.task.service.reader.ArgumentKey.*;

import java.util.List;
import org.springframework.stereotype.Component;
import ru.korona.task.models.AppArguments;
import ru.korona.task.models.StatisticsConfig;

@Component
public class PathArgumentInitializer implements ArgumentsInitializer {
    @Override
    public void initialize(String argumentValue, AppArguments appArguments) {
        StatisticsConfig statisticsConfig = appArguments.getStatisticsConfig();
        if (statisticsConfig == null) {
            statisticsConfig = new StatisticsConfig();
            appArguments.setStatisticsConfig(statisticsConfig);
        }
        statisticsConfig.setOutputFilePath(argumentValue);
    }

    @Override
    public List<String> argumentKeys() {
        return List.of(PATH_ARGUMENT_PREFIX);
    }
}

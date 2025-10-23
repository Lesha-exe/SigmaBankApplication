package ru.korona.task.service.reader;

import static ru.korona.task.service.reader.ArgumentKey.OUTPUT_ARGUMENT_PREFIX;
import static ru.korona.task.service.reader.ArgumentKey.OUTPUT_ARGUMENT_SHORT_PREFIX;

import java.util.List;
import org.springframework.stereotype.Component;
import ru.korona.task.models.AppArguments;
import ru.korona.task.models.StatisticsConfig;
import ru.korona.task.objectparameters.OutputType;

@Component
public class StatisticsOutputTypeInitializer implements ArgumentsInitializer {
    @Override
    public void initialize(String argumentValue, AppArguments appArguments) {
        StatisticsConfig statisticsConfig = appArguments.getStatisticsConfig();
        if (statisticsConfig == null) {
            statisticsConfig = new StatisticsConfig();
            appArguments.setStatisticsConfig(statisticsConfig);
        }
        statisticsConfig.setOutputType(OutputType.from(argumentValue));
    }

    @Override
    public List<String> argumentKeys() {
        return List.of(OUTPUT_ARGUMENT_PREFIX, OUTPUT_ARGUMENT_SHORT_PREFIX);
    }
}

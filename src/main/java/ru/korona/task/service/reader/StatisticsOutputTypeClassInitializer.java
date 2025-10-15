package ru.korona.task.service.reader;

import org.springframework.stereotype.Component;
import ru.korona.task.models.AppArguments;
import ru.korona.task.models.StatisticsConfig;
import ru.korona.task.objectparameters.OutputType;

import java.util.List;

import static ru.korona.task.service.reader.ArgumentKey.OUTPUT_ARGUMENT_PREFIX;
import static ru.korona.task.service.reader.ArgumentKey.OUTPUT_ARGUMENT_SHORT_PREFIX;

@Component
public class StatisticsOutputTypeClassInitializer implements ArgumentsInitializer {
    @Override
    public void initialize(String argumentValue, AppArguments appArguments) {
        StatisticsConfig statisticsConfig = appArguments.getStatisticsConfig();
        if(statisticsConfig == null){
            statisticsConfig = new StatisticsConfig();
        }
        statisticsConfig.setOutputType(OutputType.from(argumentValue));
    }

    @Override
    public List<String> argumentKeys() {
        return List.of(OUTPUT_ARGUMENT_PREFIX, OUTPUT_ARGUMENT_SHORT_PREFIX);
    }
}

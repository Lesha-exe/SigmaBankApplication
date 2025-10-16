package ru.korona.task.service.reader;

import org.springframework.stereotype.Component;
import ru.korona.task.models.AppArguments;
import ru.korona.task.models.StatisticsConfig;

import java.util.List;

import static ru.korona.task.service.reader.ArgumentKey.STATISTICS_FLAG;

@Component
public class StatisticsIsPresentInitializer implements ArgumentsInitializer{
    @Override
    public void initialize(String argumentValue, AppArguments appArguments) {
        StatisticsConfig statisticsConfig = appArguments.getStatisticsConfig();
        if(statisticsConfig == null){
            statisticsConfig = new StatisticsConfig();
            appArguments.setStatisticsConfig(statisticsConfig);
        }
        statisticsConfig.setStatisticsPresent(true);
    }

    @Override
    public List<String> argumentKeys() {
        return List.of(STATISTICS_FLAG);
    }
}

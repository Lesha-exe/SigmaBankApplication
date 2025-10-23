package ru.korona.task.repository.statisticsstorage;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import ru.korona.task.models.DepartmentStatistics;
import ru.korona.task.repository.StatisticsRepository;

import java.util.List;

@Component
@Profile("console")
public class StatisticsConsoleStorage implements StatisticsRepository {
    @Override
    public void storeStatistics(List<DepartmentStatistics> departmentStatisticsList) {
    }
}

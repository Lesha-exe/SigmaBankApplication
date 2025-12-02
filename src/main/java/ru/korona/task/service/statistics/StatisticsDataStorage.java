package ru.korona.task.service.statistics;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.korona.task.models.DepartmentStatistics;
import ru.korona.task.repository.StatisticsRepository;
import ru.korona.task.service.FileService;

@Component
@Slf4j
public class StatisticsDataStorage {
    private final List<String> statisticsHeaders;
    private final StatisticsRepository statisticsRepository;

    public StatisticsDataStorage(
            @Value("${statistics.header}") List<String> statisticsHeaders,
            StatisticsRepository statisticsRepository,
            FileService fileService) {
        this.statisticsHeaders = statisticsHeaders;
        this.statisticsRepository = statisticsRepository;
    }

    public void storeStatistics(List<DepartmentStatistics> departmentStatisticsList) {
        statisticsRepository.storeStatistics(departmentStatisticsList);
    }
}

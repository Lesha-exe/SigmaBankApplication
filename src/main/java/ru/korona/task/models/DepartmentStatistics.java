package ru.korona.task.models;

import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.korona.task.outputSettings.StatisticsType;

@AllArgsConstructor
@Getter
public class DepartmentStatistics {
    String departmentName;
    Map<StatisticsType, Double> statisticsData;
}

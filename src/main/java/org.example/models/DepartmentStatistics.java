package org.example.models;

import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.outputSettings.StatisticsType;

@AllArgsConstructor
@Getter
public class DepartmentStatistics {
    String departmentName;
    Map<StatisticsType, Double> statisticsData;
}

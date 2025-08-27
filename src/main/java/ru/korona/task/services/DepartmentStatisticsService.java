package ru.korona.task.services;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import ru.korona.task.models.AppArguments;
import ru.korona.task.models.Department;
import ru.korona.task.models.DepartmentStatistics;
import ru.korona.task.outputSettings.StatisticsType;

public class DepartmentStatisticsService {
    private List<StatisticsCalculator> statisticsCalculator;

    public List<DepartmentStatistics> calculateStatistics(
            List<Department> departments, AppArguments appArguments) {
        if (!appArguments.getStatisticsConfig().getIsStatisticsPresent()) {
            return null;
        }
        return departments.stream()
                .map(
                        department ->
                                new DepartmentStatistics(
                                        department.getManager().getDepartment(),
                                        calculateStatistics(department)))
                .toList();
    }

    private Map<StatisticsType, Double> calculateStatistics(Department department) {
        return statisticsCalculator.stream()
                .collect(
                        Collectors.toMap(
                                StatisticsCalculator::getType,
                                statisticsCalculator ->
                                        statisticsCalculator
                                                .calculate(
                                                        department)));
    }
}

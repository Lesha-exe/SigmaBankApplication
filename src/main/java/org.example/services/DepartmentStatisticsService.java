package org.example.services;

import java.util.List;
import java.util.stream.Collectors;
import org.example.models.AppArguments;
import org.example.models.Department;
import org.example.models.DepartmentStatistics;

public class DepartmentStatisticsService {
    private List<StatisticsCalculator> statisticsCalculator;

    public List<DepartmentStatistics> calculateStatistics(
            List<Department> departments, AppArguments appArguments) {
        return departments.stream()
                .map(
                        department ->
                                new DepartmentStatistics(
                                        department.getManager().getDepartment(),
                                        statisticsCalculator.stream()
                                                .collect(
                                                        Collectors.toMap(
                                                                StatisticsCalculator::getType,
                                                                statisticsCalculator ->
                                                                        statisticsCalculator
                                                                                .calculate(
                                                                                        department)))))
                .toList();
    }
}

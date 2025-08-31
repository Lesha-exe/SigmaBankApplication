package ru.korona.task.service.statistics;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.korona.task.models.Department;
import ru.korona.task.models.DepartmentStatistics;
import ru.korona.task.objectparameters.StatisticsType;

@Component
@RequiredArgsConstructor
public class DepartmentStatisticsService {
    private final List<StatisticsCalculator> statisticsCalculator;

    public List<DepartmentStatistics> calculateStatistics(List<Department> departments) {
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
                                        statisticsCalculator.calculate(department)));
    }
}

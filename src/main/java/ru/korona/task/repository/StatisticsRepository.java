package ru.korona.task.repository;

import ru.korona.task.models.DepartmentStatistics;

import java.util.List;

public interface StatisticsRepository {
    void storeStatistics(List<DepartmentStatistics> departmentStatisticsList);
}

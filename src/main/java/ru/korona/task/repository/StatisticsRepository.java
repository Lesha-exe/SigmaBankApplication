package ru.korona.task.repository;

import java.util.List;
import ru.korona.task.models.DepartmentStatistics;

public interface StatisticsRepository {
    void storeStatistics(List<DepartmentStatistics> departmentStatisticsList);
}

package ru.korona.task.repository.statisticsstorage;

import java.util.List;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.korona.task.models.DepartmentStatistics;
import ru.korona.task.objectparameters.StatisticsType;
import ru.korona.task.repository.StatisticsRepository;

@Component
@Profile({"JdbcTemplate"})
public class StatisticsDataJdbcTemplateRepositoryImpl implements StatisticsRepository {
    private final JdbcTemplate jdbcTemplate;

    public StatisticsDataJdbcTemplateRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional
    public void storeStatistics(List<DepartmentStatistics> departmentStatisticsList) {
        for (DepartmentStatistics departmentStatistics : departmentStatisticsList) {
            storeStatisticsData(departmentStatistics);
        }
    }

    private void storeStatisticsData(DepartmentStatistics departmentStatistics) {
        jdbcTemplate.update(
                insertStatisticsDataQuery(),
                departmentStatistics.getDepartmentName(),
                departmentStatistics.getStatisticsData().get(StatisticsType.MIN_SALARY),
                departmentStatistics.getStatisticsData().get(StatisticsType.MAX_SALARY),
                departmentStatistics.getStatisticsData().get(StatisticsType.MID_SALARY));
    }

    private String insertStatisticsDataQuery() {
        return """
                INSERT INTO statistics_data (department_name, min, max, mid, creation_timestamp)
                VALUES (?, ?, ?, ?, NOW())
                    ON CONFLICT (department_name) DO UPDATE
                        SET min = EXCLUDED.min,
                        max = EXCLUDED.max,
                        mid = EXCLUDED.mid
                """;
    }
}

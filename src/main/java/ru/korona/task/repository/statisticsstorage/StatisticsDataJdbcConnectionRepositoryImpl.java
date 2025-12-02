package ru.korona.task.repository.statisticsstorage;

import java.sql.*;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import ru.korona.task.models.DepartmentStatistics;
import ru.korona.task.objectparameters.StatisticsType;
import ru.korona.task.repository.StatisticsRepository;

@Component
@Profile("JdbcConnection")
@Slf4j
public class StatisticsDataJdbcConnectionRepositoryImpl implements StatisticsRepository {
    private String url;
    private String username;
    private String password;

    public StatisticsDataJdbcConnectionRepositoryImpl(
            @Value("${spring.datasource.url}") String url,
            @Value("${spring.datasource.username}") String username,
            @Value("${spring.datasource.password}") String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    @Override
    public void storeStatistics(List<DepartmentStatistics> departmentStatisticsList) {
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            for (DepartmentStatistics departmentStatistics : departmentStatisticsList) {
                try {
                    connection.setAutoCommit(false);
                    storeStatisticsData(departmentStatistics, connection);
                    connection.commit();
                } catch (SQLException exception) {
                    log.error(
                            "Error while saving statistics data: {}",
                            departmentStatistics,
                            exception);
                    try {
                        connection.rollback();
                        log.warn(
                                "Transaction for statistics data: {} rolled back",
                                departmentStatistics,
                                exception);
                    } catch (SQLException rollbackException) {
                        log.error("Rollback failure", rollbackException);
                    }
                } finally {
                    connection.setAutoCommit(true);
                }
            }
        } catch (SQLException exception) {
            log.error("Failed to store statistics data", exception);
        }
    }

    private void storeStatisticsData(
            DepartmentStatistics departmentStatistics, Connection connection) {
        try (PreparedStatement preparedStatement =
                connection.prepareStatement(insertStatisticsData())) {
            preparedStatement.setString(1, departmentStatistics.getDepartmentName());
            preparedStatement.setDouble(
                    2, departmentStatistics.getStatisticsData().get(StatisticsType.MIN_SALARY));
            preparedStatement.setDouble(
                    3, departmentStatistics.getStatisticsData().get(StatisticsType.MAX_SALARY));
            preparedStatement.setDouble(
                    4, departmentStatistics.getStatisticsData().get(StatisticsType.MID_SALARY));
            preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            log.error(
                    "Cannot save statistics data for department: department name=[{}] to database",
                    departmentStatistics.getDepartmentName(),
                    exception);
        }
    }

    private String insertStatisticsData() {
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

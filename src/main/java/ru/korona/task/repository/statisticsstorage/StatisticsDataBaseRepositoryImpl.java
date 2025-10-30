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
@Profile("database")
@Slf4j
public class StatisticsDataBaseRepositoryImpl implements StatisticsRepository {
    private String url;
    private String username;
    private String password;

    public StatisticsDataBaseRepositoryImpl(
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
            createTableIfNotExists(connection);
            for (DepartmentStatistics departmentStatistics : departmentStatisticsList) {
                storeStatisticsData(departmentStatistics, connection);
            }
        } catch (SQLException exception) {
            log.error("Cannot connect to data base! Exception: " + exception.getMessage());
        }
    }

    private void storeStatisticsData(
            DepartmentStatistics departmentStatistics, Connection connection) {
        try (PreparedStatement preparedStatement =
                connection.prepareStatement(insertStatisticsDataRequest())) {
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
                    "Cannot save statistics data for department: "
                            + departmentStatistics.getDepartmentName()
                            + " to data base!"
                            + " Exception: "
                            + exception.getMessage());
        }
    }

    private void createTableIfNotExists(Connection connection) {
        try (Statement statement = connection.createStatement()) {
            statement.execute(createTableRequest());
        } catch (SQLException exception) {
            log.error(
                    "Cannot send table creation request!"
                            + "Table name: statistics_data. "
                            + "Exception: "
                            + exception.getMessage());
        }
    }

    private String createTableRequest() {
        return """
                    CREATE TABLE IF NOT EXISTS statistics_data (
                        department_name VARCHAR(255) PRIMARY KEY,
                        min DOUBLE PRECISION,
                        max DOUBLE PRECISION,
                        mid DOUBLE PRECISION
                );
                """;
    }

    private String insertStatisticsDataRequest() {
        return """
                INSERT INTO statistics_data (department_name, min, max, mid)
                VALUES (?, ?, ?, ?)
                    ON CONFLICT (department_name) DO UPDATE
                        SET min = EXCLUDED.min,
                        max = EXCLUDED.max,
                        mid = EXCLUDED.mid;
                """;
    }
}

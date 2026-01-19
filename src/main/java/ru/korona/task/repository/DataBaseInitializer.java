package ru.korona.task.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
@Profile({"JdbcConnection", "JdbcTemplate"})
@Slf4j
public class DataBaseInitializer implements ApplicationListener<ContextRefreshedEvent> {
    private final String url;
    private final String username;
    private final String password;

    public DataBaseInitializer(
            @Value("${spring.datasource.url}") String url,
            @Value("${spring.datasource.username}") String username,
            @Value("${spring.datasource.password}") String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            Statement stmt = connection.createStatement();
            stmt.execute(createManagersTable());
            stmt.execute(createEmployeesTable());
            stmt.execute(createInvalidDataTable());
            stmt.execute(createStatisticsDataTable());
        } catch (SQLException exception) {
            log.error("Failed to create tables. ", exception);
        }
    }

    private static String createManagersTable() {
        return """
                    CREATE TABLE IF NOT EXISTS managers (
                        id BIGINT PRIMARY KEY,
                        name VARCHAR(255) NOT NULL,
                        salary DOUBLE PRECISION,
                        department VARCHAR(255),
                        creation_timestamp TIMESTAMPTZ DEFAULT NOW()
                    );
                """;
    }

    private static String createEmployeesTable() {
        return """
                    CREATE TABLE IF NOT EXISTS employees (
                        id BIGINT PRIMARY KEY,
                        name VARCHAR(255) NOT NULL,
                        salary DOUBLE PRECISION,
                        manager_id BIGINT REFERENCES managers(id),
                        creation_timestamp TIMESTAMPTZ DEFAULT NOW()
                    );
                """;
    }

    private static String createInvalidDataTable() {
        return """
                    CREATE TABLE IF NOT EXISTS invalid_data (
                        id SERIAL PRIMARY KEY,
                        data TEXT NOT NULL,
                        creation_timestamp TIMESTAMPTZ DEFAULT NOW()
                );
                """;
    }

    private static String createStatisticsDataTable() {
        return """
                    CREATE TABLE IF NOT EXISTS statistics_data (
                        department_name VARCHAR(255) PRIMARY KEY,
                        min DOUBLE PRECISION,
                        max DOUBLE PRECISION,
                        mid DOUBLE PRECISION,
                        creation_timestamp TIMESTAMPTZ DEFAULT NOW()
                );
                """;
    }
}

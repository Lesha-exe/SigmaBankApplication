package ru.korona.task.repository.invaliddatastorage;

import java.sql.*;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import ru.korona.task.models.Worker;
import ru.korona.task.repository.InvalidDataRepository;

@Component
@Profile("JdbcConnection")
@Slf4j
public class InvalidDataJdbcConnectionRepository implements InvalidDataRepository {
    private final String url;
    private final String username;
    private final String password;

    public InvalidDataJdbcConnectionRepository(
            @Value("${spring.datasource.url}") String url,
            @Value("${spring.datasource.username}") String username,
            @Value("${spring.datasource.password}") String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    @Override
    public void storeData(List<Worker> workersWithIncorrectData) {
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            List<String> invalidData = invalidDataToString(workersWithIncorrectData);
            for (String invalidDataLine : invalidData) {
                storeInvalidData(invalidDataLine, connection);
            }
        } catch (SQLException exception) {
            log.error("Failed to store invalid data", exception);
        }
    }

    private void storeInvalidData(String invalidDataLine, Connection connection) {
        try (PreparedStatement stmt = connection.prepareStatement(insertInvalidDataRequest())) {
            stmt.setString(1, invalidDataLine);
            stmt.executeUpdate();
        } catch (SQLException exception) {
            log.error("Cannot save invalid data to database", exception);
        }
    }

    private List<String> invalidDataToString(List<Worker> workersWithIncorrectData) {
        return workersWithIncorrectData.stream().map(String::valueOf).toList();
    }

    private String insertInvalidDataRequest() {
        return """
                INSERT INTO invalid_data (data)
                VALUES (?)
                """;
    }
}

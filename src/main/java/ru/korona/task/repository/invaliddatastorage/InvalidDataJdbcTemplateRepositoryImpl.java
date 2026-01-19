package ru.korona.task.repository.invaliddatastorage;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.korona.task.models.Worker;
import ru.korona.task.repository.InvalidDataRepository;

@Component
@Profile("JdbcTemplate")
@RequiredArgsConstructor
public class InvalidDataJdbcTemplateRepositoryImpl implements InvalidDataRepository {

    private final JdbcTemplate jdbcTemplate;
    private final ObjectMapper objectMapper;

    @Override
    @Transactional
    public void storeData(List<Worker> workersWithIncorrectData) {
        for (Worker worker : workersWithIncorrectData) {
            jdbcTemplate.update(insertInvalidDataQuery(), invalidDataToJson(worker));
        }
    }

    private String invalidDataToJson(Worker worker) {
        try {
            return objectMapper.writeValueAsString(worker);
        } catch (JsonProcessingException exception) {
            throw new RuntimeException("Failed to serialize worker", exception);
        }
    }

    private String insertInvalidDataQuery() {
        return """
                INSERT INTO invalid_data (data, creation_timestamp)
                VALUES (?::jsonb, NOW())
                """;
    }
}

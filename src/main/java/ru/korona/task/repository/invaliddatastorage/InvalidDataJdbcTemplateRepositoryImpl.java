package ru.korona.task.repository.invaliddatastorage;

import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.korona.task.models.Department;
import ru.korona.task.models.Worker;
import ru.korona.task.repository.DepartmentRepository;
import ru.korona.task.repository.InvalidDataRepository;

import java.util.List;

@Component
@Profile("JdbcTemplate")
public class InvalidDataJdbcTemplateRepositoryImpl implements InvalidDataRepository {

    private final JdbcTemplate jdbcTemplate;

    public InvalidDataJdbcTemplateRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    @Transactional
    public void storeData(List<Worker> workersWithIncorrectData) {
        List<String> invalidData = invalidDataToString(workersWithIncorrectData);
        for(String invalidDataLine : invalidData) {
            storeInvalidData(invalidDataLine);
        }
    }

    private void storeInvalidData(String invalidData) {
        jdbcTemplate.update(insertInvalidData(), invalidData);
    }

    private List<String> invalidDataToString(List<Worker> workersWithIncorrectData) {
        return workersWithIncorrectData.stream().map(String::valueOf).toList();
    }

    private String insertInvalidData() {
        return """
                INSERT INTO invalid_data (data, creation_timestamp)
                VALUES (?, NOW())
                """;
    }
}

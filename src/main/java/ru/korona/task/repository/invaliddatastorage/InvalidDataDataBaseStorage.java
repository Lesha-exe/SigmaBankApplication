package ru.korona.task.repository.invaliddatastorage;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import ru.korona.task.models.Worker;
import ru.korona.task.repository.InvalidDataRepository;

import java.util.List;

@Component
@Profile("data base")
public class InvalidDataDataBaseStorage implements InvalidDataRepository {
    @Override
    public void storeData(List<Worker> workersWithIncorrectData) {
    }
}

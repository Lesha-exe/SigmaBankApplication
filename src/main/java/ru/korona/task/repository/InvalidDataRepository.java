package ru.korona.task.repository;

import java.util.List;
import ru.korona.task.models.Worker;

public interface InvalidDataRepository {
    void storeData(List<Worker> workersWithIncorrectData);
}

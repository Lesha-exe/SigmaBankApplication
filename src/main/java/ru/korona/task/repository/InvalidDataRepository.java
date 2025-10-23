package ru.korona.task.repository;

import ru.korona.task.models.Worker;

import java.util.List;

public interface InvalidDataRepository {
    void storeData(List<Worker> workersWithIncorrectData);
}

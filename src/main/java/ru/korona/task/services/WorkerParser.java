package ru.korona.task.services;

import ru.korona.task.models.Worker;

public interface WorkerParser {
    Worker parse(String[] workerData);
}

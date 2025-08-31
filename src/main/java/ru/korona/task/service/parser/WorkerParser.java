package ru.korona.task.service.parser;

import ru.korona.task.models.Worker;

public interface WorkerParser {
    Worker parse(String[] workerData);
}

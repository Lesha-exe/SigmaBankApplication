package ru.korona.task.service.reader;

import ru.korona.task.models.AppArguments;

import java.util.List;

public interface ArgumentsInitializer {
    void initialize(String argumentValue, AppArguments appArguments);
    List<String> argumentKeys();
}

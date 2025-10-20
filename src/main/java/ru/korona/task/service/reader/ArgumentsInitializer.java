package ru.korona.task.service.reader;

import java.util.List;
import ru.korona.task.models.AppArguments;

public interface ArgumentsInitializer {
    void initialize(String argumentValue, AppArguments appArguments);

    List<String> argumentKeys();
}

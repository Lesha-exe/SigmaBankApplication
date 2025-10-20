package ru.korona.task.service.reader;

import static ru.korona.task.service.reader.ArgumentKey.SORT_ARGUMENT_PREFIX;
import static ru.korona.task.service.reader.ArgumentKey.SORT_ARGUMENT_SHORT_PREFIX;

import java.util.List;
import org.springframework.stereotype.Component;
import ru.korona.task.models.AppArguments;
import ru.korona.task.objectparameters.SortType;

@Component
public class SortArgumentInitializer implements ArgumentsInitializer {
    @Override
    public void initialize(String argumentValue, AppArguments appArguments) {
        appArguments.setSortType(SortType.from(argumentValue));
    }

    @Override
    public List<String> argumentKeys() {
        return List.of(SORT_ARGUMENT_PREFIX, SORT_ARGUMENT_SHORT_PREFIX);
    }
}

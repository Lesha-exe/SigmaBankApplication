package ru.korona.task.service.reader;

import org.springframework.stereotype.Component;
import ru.korona.task.models.AppArguments;
import ru.korona.task.objectparameters.OrderType;

import java.util.List;

import static ru.korona.task.service.reader.ArgumentKey.SPRING_PROFILE_PREFIX;

@Component
public class SpringProfileArgumentInitializer implements ArgumentsInitializer{
    @Override
    public void initialize(String argumentValue, AppArguments appArguments) {
    }

    @Override
    public List<String> argumentKeys() {
        return List.of(SPRING_PROFILE_PREFIX);
    }
}

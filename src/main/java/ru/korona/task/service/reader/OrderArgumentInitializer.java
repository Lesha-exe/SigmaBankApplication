package ru.korona.task.service.reader;

import static ru.korona.task.service.reader.ArgumentKey.ORDER_ARGUMENT_PREFIX;

import java.util.List;
import org.springframework.stereotype.Component;
import ru.korona.task.models.AppArguments;
import ru.korona.task.objectparameters.OrderType;

@Component
public class OrderArgumentInitializer implements ArgumentsInitializer {
    @Override
    public void initialize(String argumentValue, AppArguments appArguments) {
        appArguments.setOrder(OrderType.from(argumentValue));
    }

    @Override
    public List<String> argumentKeys() {
        return List.of(ORDER_ARGUMENT_PREFIX);
    }
}

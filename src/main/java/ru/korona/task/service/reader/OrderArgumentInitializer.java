package ru.korona.task.service.reader;

import org.springframework.stereotype.Component;
import ru.korona.task.models.AppArguments;
import ru.korona.task.objectparameters.OrderType;

import java.util.List;

import static ru.korona.task.service.reader.ArgumentKey.ORDER_ARGUMENT_PREFIX;

@Component
public class OrderArgumentInitializer implements ArgumentsInitializer{
    @Override
    public void initialize(String argumentValue, AppArguments appArguments) {
        appArguments.setOrder(OrderType.from(argumentValue));
    }

    @Override
    public List<String> argumentKeys() {
        return List.of(ORDER_ARGUMENT_PREFIX);
    }
}

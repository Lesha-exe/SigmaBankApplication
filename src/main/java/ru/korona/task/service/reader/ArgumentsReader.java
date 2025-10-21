package ru.korona.task.service.reader;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.korona.task.exceptions.AppArgumentsException;
import ru.korona.task.models.AppArguments;
import ru.korona.task.objectparameters.OrderType;
import ru.korona.task.objectparameters.OutputType;
import ru.korona.task.objectparameters.SortType;

@Component
public class ArgumentsReader {
    private Map<String, ArgumentsInitializer> argumentInitializerMap;
    private ArgumentsValidator argumentsValidator;

    public ArgumentsReader(List<ArgumentsInitializer> argumentInitializers) {
        argumentInitializerMap = new HashMap<>();
        argumentInitializers.forEach(
                argumentInitializer -> {
                    final List<String> keys = argumentInitializer.argumentKeys();
                    keys.forEach(key -> argumentInitializerMap.put(key, argumentInitializer));
                });
    }

    public AppArguments readArguments(String[] args) {
        AppArguments appArguments = new AppArguments();
        for (String arg : args) {
            Argument argument = parseArgument(arg);
            ArgumentsInitializer argumentsInitializer =
                    argumentInitializerMap.get(argument.getKey());
            argumentsInitializer.initialize(argument.getValue(), appArguments);
        }
        ArgumentsValidator.validateArguments(appArguments);
        return appArguments;
    }

    private static Argument parseArgument(String arg) {
        if (arg.contains("=")) {
            final String[] argParts = arg.split("=");
            return new Argument(argParts[0], argParts[1]);
        } else {
            return new Argument(arg, arg);
        }
    }

    @AllArgsConstructor
    @Getter
    private static class Argument {
        private String key;
        private String value;
    }
}

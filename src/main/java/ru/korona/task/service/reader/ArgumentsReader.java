package ru.korona.task.service.reader;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Component;
import ru.korona.task.exceptions.AppArgumentsException;
import ru.korona.task.models.AppArguments;
import ru.korona.task.objectparameters.OrderType;
import ru.korona.task.objectparameters.OutputType;
import ru.korona.task.objectparameters.SortType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ArgumentsReader {
    private Map<String, ArgumentsInitializer> argumentInitializerMap;

    public ArgumentsReader(List<ArgumentsInitializer> argumentInitializers) {
        argumentInitializerMap = new HashMap<>();
        argumentInitializers.forEach(argumentInitializer -> {
            final List<String> keys = argumentInitializer.argumentKeys();
            keys.forEach(key -> argumentInitializerMap.put(key, argumentInitializer));
        });
    }

    public AppArguments readArguments(String[] args) {
        AppArguments appArguments = new AppArguments();
        for (String arg : args) {
            Argument argument = parseArgument(arg);
            ArgumentsInitializer argumentsInitializer = argumentInitializerMap.get(argument.getKey());
            argumentsInitializer.initialize(argument.getValue(), appArguments);
        }
        validateArguments(appArguments);
        return appArguments;
    }

    private static Argument parseArgument(String arg) {
        if (arg.contains("=")){
            final String[] argParts = arg.split("=");
            return new Argument(argParts[0], argParts[1]);
        } else {
            return new Argument(arg, arg);
        }

    }

    private void validateArguments(AppArguments appArguments) {
        SortType sortType = appArguments.getSortType();
        OrderType orderType = appArguments.getOrder();
        if ((sortType == null) && (orderType != null)) {
            throw new AppArgumentsException(
                    "You entered order type," + " but sort type parameter is missing...");
        }
        boolean statFlag = appArguments.getStatisticsConfig().isStatisticsPresent();
        OutputType outputType = appArguments.getStatisticsConfig().getOutputType();
        if (!statFlag && outputType != null) {
            throw new AppArgumentsException("Missing --stat operator ");
        }
        String outputFilePath = appArguments.getStatisticsConfig().getOutputFilePath();
        if (OutputType.FILE == outputType && outputFilePath == null) {
            throw new AppArgumentsException("Missing output file path...");
        }
        if (outputFilePath != null && outputType == null) {
            throw new AppArgumentsException("Missing output type parameter...");
        }
    }

    @AllArgsConstructor
    @Getter
    private static class Argument {
        private String key;
        private String value;
    }
}

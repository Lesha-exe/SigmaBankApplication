package ru.korona.task.service.reader;

import org.springframework.stereotype.Component;
import ru.korona.task.exceptions.AppArgumentsException;
import ru.korona.task.models.AppArguments;
import ru.korona.task.objectparameters.OrderType;
import ru.korona.task.objectparameters.OutputType;
import ru.korona.task.objectparameters.SortType;

public class ArgumentsValidator {
    public static void validateArguments(AppArguments appArguments) {
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
}

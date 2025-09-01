package ru.korona.task.service.reader;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.korona.task.exceptions.*;
import ru.korona.task.models.AppArguments;
import ru.korona.task.models.StatisticsConfig;
import ru.korona.task.objectparameters.OrderType;
import ru.korona.task.objectparameters.OutputType;
import ru.korona.task.objectparameters.SortType;

@Component
@Slf4j
public class ArgumentsReader {
    private static final String SORT_ARGUMENT_PREFIX = "--sort=";
    private static final String SORT_ARGUMENT_SHORT_PREFIX = "-s=";
    private static final String ORDER_ARGUMENT_PREFIX = "--order=";
    private static final String STATISTICS_FLAG = "--stat";
    private static final String OUTPUT_ARGUMENT_PREFIX = "--output=";
    private static final String OUTPUT_ARGUMENT_SHORT_PREFIX = "-o=";
    private static final String PATH_ARGUMENT_PREFIX = "--path=";

    public AppArguments readArguments(String[] args) {
        AppArguments.AppArgumentsBuilder appArgumentsBuilder = AppArguments.builder();
        StatisticsConfig.StatisticsConfigBuilder statisticsConfigBuilder =
                StatisticsConfig.builder();
        if (args.length > 0) {
            for (String arg : args) {
                if (arg.startsWith(SORT_ARGUMENT_PREFIX)
                        || arg.startsWith(SORT_ARGUMENT_SHORT_PREFIX)) {
                    appArgumentsBuilder.sortType(SortType.from(getParameterValue(arg)));
                } else if (arg.startsWith(ORDER_ARGUMENT_PREFIX)) {
                    appArgumentsBuilder.order(OrderType.from(getParameterValue(arg)));
                } else if (arg.equals(STATISTICS_FLAG)) {
                    statisticsConfigBuilder.isStatisticsPresent(true);
                } else if (arg.startsWith(OUTPUT_ARGUMENT_PREFIX)
                        || arg.startsWith(OUTPUT_ARGUMENT_SHORT_PREFIX)) {
                    statisticsConfigBuilder.outputType(OutputType.from(getParameterValue(arg)));
                } else if (arg.startsWith(PATH_ARGUMENT_PREFIX)) {
                    statisticsConfigBuilder.outputFilePath(getParameterValue(arg));
                } else {
                    throw new IllegalArgumentException("Unknown argument: " + arg);
                }
            }
        } else {
            throw new AppArgumentsException("No command line...");
        }
        appArgumentsBuilder.statisticsConfig(statisticsConfigBuilder.build());
        AppArguments appArgument = appArgumentsBuilder.build();
        validateArguments(appArgument);
        return appArgument;
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

    private String getParameterValue(String arg) {
        return arg.substring(arg.indexOf("=") + 1).toLowerCase();
    }
}

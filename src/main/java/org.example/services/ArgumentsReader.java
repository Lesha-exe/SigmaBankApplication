package org.example.services;

import lombok.Builder;
import org.example.exceptions.MissingFilePathException;
import org.example.exceptions.MissingOutputTypeParameterException;
import org.example.exceptions.MissingSortTypeException;
import org.example.exceptions.MissingStatOperatorException;
import org.example.models.AppArguments;
import org.example.models.StatisticsConfig;
import org.example.outputSettings.OrderType;
import org.example.outputSettings.OutputType;
import org.example.outputSettings.SortType;
import org.springframework.stereotype.Component;

@Component
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
        StatisticsConfig.StatisticsConfigBuilder statisticsConfigBuilder = StatisticsConfig.builder();
        if (args.length > 0) {
            for (String arg : args) {
                if (arg.startsWith(SORT_ARGUMENT_PREFIX) || arg.startsWith(SORT_ARGUMENT_SHORT_PREFIX)) {
                    appArgumentsBuilder.sortType(SortType.from(returnParameter(arg)));
                } else if (arg.startsWith(ORDER_ARGUMENT_PREFIX)) {
                    appArgumentsBuilder.order(OrderType.from(returnParameter(arg)));
                } else if (arg.equals(STATISTICS_FLAG)) {
                    statisticsConfigBuilder.statFlag(true);
                } else if (arg.startsWith(OUTPUT_ARGUMENT_PREFIX) || arg.startsWith(OUTPUT_ARGUMENT_SHORT_PREFIX)) {
                    statisticsConfigBuilder.outputType(OutputType.from(returnParameter(arg)));
                } else if (arg.startsWith(PATH_ARGUMENT_PREFIX)) {
                    statisticsConfigBuilder.outputFilePath(returnParameter(arg));
                } else {
                    throw new IllegalArgumentException("ERROR! Unknown argument: " + arg);
                }
            }
        } else {
            System.out.println("ERROR! No command line...");
        }
        appArgumentsBuilder.statisticsConfig(statisticsConfigBuilder.build());
        AppArguments appArgument = appArgumentsBuilder.build();
        validateArguments(appArgument);
        return appArgument;
    }

    private void validateArguments(AppArguments appArguments) {
        SortType sortType = appArguments.getSortType();
        OrderType orderType = appArguments.getOrder();
        Boolean statFlag = appArguments.getStatisticsConfig().getStatFlag();
        OutputType outputType = appArguments.getStatisticsConfig().getOutputType();
        String outputFilePath = appArguments.getStatisticsConfig().getOutputFilePath();
        if (sortType.equals(SortType.UNDEFINED)) {
            appArguments.setSortType(SortType.NAME);
        }
        if ((sortType == null) && (orderType != null)) {
            throw new MissingSortTypeException("ERROR! You entered order type," +
                    " but sort type parameter is missing...");
        }
        if (!statFlag && outputType != null) {
            throw new MissingStatOperatorException("ERROR! Missing --stat operator ");
        }
        if (outputType.equals(OutputType.UNDEFINED)) {
            appArguments.getStatisticsConfig().setOutputType(OutputType.CONSOLE);
        }
        if (outputType.equals(OutputType.FILE) && outputFilePath == null) {
            throw new MissingFilePathException("ERROR! Missing output file path...");
        }
        if (outputFilePath != null && outputType == null) {
            throw new MissingOutputTypeParameterException("ERROR! Missing output type parameter...");
        }
    }

    private String returnParameter(String arg) {
        return arg.substring(arg.indexOf("=") + 1).toUpperCase();
    }
}

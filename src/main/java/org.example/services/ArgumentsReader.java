package org.example.services;

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
    public AppArguments readArguments(String[] args) {
        AppArguments appArgument = new AppArguments();
        StatisticsConfig statisticsConfig = new StatisticsConfig();
        if (args.length > 0) {
            for (String arg : args) {
                if (arg.startsWith("--sort=") || arg.startsWith("-s=")) {
                    appArgument.setSortType(SortType.from(returnParameter(arg)));
                } else if (arg.startsWith("--order=")) {
                    appArgument.setOrder(OrderType.from(returnParameter(arg)));
                } else if (arg.equals("--stat")) {
                    statisticsConfig.setStatFlag(true);
                } else if (arg.startsWith("--output=") || arg.startsWith("-o=")) {
                    statisticsConfig.setOutputType(OutputType.from(returnParameter(arg)));
                } else if (arg.startsWith("--path=")) {
                    statisticsConfig.setOutputFilePath(returnParameter(arg));
                } else {
                    throw new IllegalArgumentException("ERROR! Unknown argument: " + arg);
                }
            }
        } else {
            System.out.println("ERROR! No command line...");
        }
        appArgument.setStatisticsConfig(statisticsConfig);
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

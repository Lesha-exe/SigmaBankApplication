package ru.korona.task.reader;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.korona.task.models.AppArguments;
import ru.korona.task.objectparameters.OrderType;
import ru.korona.task.objectparameters.OutputType;
import ru.korona.task.objectparameters.SortType;
import ru.korona.task.service.reader.*;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ArgumentsReaderTest {
    private ArgumentsReader argumentsReader;

    @BeforeEach
    void setUp(){
        List<ArgumentsInitializer> initializers = List.of(
                new OrderArgumentInitializer(),
                new PathArgumentInitializer(),
                new SortArgumentInitializer(),
                new StatisticsIsPresentInitializer(),
                new StatisticsOutputTypeInitializer());
        argumentsReader = new ArgumentsReader(initializers);
    }

    @Test
    void readArgumentsWithAllArgs(){
        // given
        String[] args = {
                "--stat",
                "--sort=name",
                "--order=asc",
                "--output=file",
                "--path=output/tmp/outputfile.txt"
        };

        // when
        AppArguments appArguments = argumentsReader.readArguments(args);

        // then
        assertThat(appArguments.getStatisticsConfig().isStatisticsPresent())
                .isEqualTo(true);
        assertThat(appArguments.getSortType())
                .isEqualTo(SortType.NAME);
        assertThat(appArguments.getOrder())
                .isEqualTo(OrderType.ASC);
        assertThat(appArguments.getStatisticsConfig().getOutputType())
                .isEqualTo(OutputType.FILE);
        assertThat(appArguments.getStatisticsConfig().getOutputFilePath())
                .isEqualTo("output/tmp/outputfile.txt");
    }

    @Test
    void readArgumentsWithoutSomeArgs(){
        // given
        String[] args = {
                "--stat",
                "--sort=salary",
                "--order=desc",
        };

        // when
        AppArguments appArguments = argumentsReader.readArguments(args);

        // then
        assertThat(appArguments.getStatisticsConfig().isStatisticsPresent())
                .isEqualTo(true);
        assertThat(appArguments.getSortType())
                .isEqualTo(SortType.SALARY);
        assertThat(appArguments.getOrder())
                .isEqualTo(OrderType.DESC);
        assertThat(appArguments.getStatisticsConfig().getOutputType())
                .isEqualTo(null);
        assertThat(appArguments.getStatisticsConfig().getOutputFilePath())
                .isEqualTo(null);
    }
}

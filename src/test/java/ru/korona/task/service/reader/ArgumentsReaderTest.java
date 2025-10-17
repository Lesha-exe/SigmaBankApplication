package ru.korona.task.service.reader;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.korona.task.models.AppArguments;
import ru.korona.task.models.StatisticsConfig;
import ru.korona.task.objectparameters.OrderType;
import ru.korona.task.objectparameters.OutputType;
import ru.korona.task.objectparameters.SortType;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ArgumentsReaderTest {
    private ArgumentsReader argumentsReader;
    private ArgumentsInitializer sortInitializer;
    private ArgumentsInitializer orderInitializer;
    private ArgumentsInitializer pathInitializer;
    private ArgumentsInitializer statisticsPresentInitializer;
    private ArgumentsInitializer statisticsOutputInitializer;

    @BeforeEach
    void setUp() {
        orderInitializer = mock(ArgumentsInitializer.class);
        when(orderInitializer.argumentKeys()).thenReturn(List.of("--order"));

        pathInitializer = mock(ArgumentsInitializer.class);
        when(orderInitializer.argumentKeys()).thenReturn(List.of("--path"));

        sortInitializer = mock(ArgumentsInitializer.class);
        when(sortInitializer.argumentKeys()).thenReturn(List.of("--sort"));

        statisticsPresentInitializer = mock(ArgumentsInitializer.class);
        when(statisticsPresentInitializer.argumentKeys()).thenReturn(List.of("--stat"));

        statisticsOutputInitializer = mock(ArgumentsInitializer.class);
        when(statisticsOutputInitializer.argumentKeys()).thenReturn(List.of("--output"));

        List<ArgumentsInitializer> initializers = List.of(
                orderInitializer,
                pathInitializer,
                sortInitializer,
                statisticsPresentInitializer,
                statisticsOutputInitializer);
        argumentsReader = new ArgumentsReader(initializers);
    }

    @Test
    void readArgumentsWithAllArgs() {
        // given
        AppArguments expectedAppArguments = new AppArguments(
                SortType.NAME,
                OrderType.ASC,
                new StatisticsConfig(
                        true,
                        OutputType.FILE,
                        "output/tmp/outputfile.txt"));
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
        assertThat(expectedAppArguments).isEqualTo(appArguments);
    }

    @Test
    void readArgumentsWithoutSomeArgs() {
        // given
        AppArguments expectedAppArguments = new AppArguments(SortType.SALARY, OrderType.DESC,
                new StatisticsConfig(true, null, null));
        String[] args = {
                "--stat",
                "--sort=salary",
                "--order=desc",
        };

        // when
        AppArguments appArguments = argumentsReader.readArguments(args);

        // then
        assertThat(expectedAppArguments).isEqualTo(appArguments);
    }
}

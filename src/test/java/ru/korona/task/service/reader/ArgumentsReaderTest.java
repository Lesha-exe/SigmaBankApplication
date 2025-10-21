package ru.korona.task.service.reader;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.korona.task.models.AppArguments;
import ru.korona.task.models.StatisticsConfig;
import ru.korona.task.objectparameters.OrderType;
import ru.korona.task.objectparameters.OutputType;
import ru.korona.task.objectparameters.SortType;

public class ArgumentsReaderTest {
    private ArgumentsReader argumentsReader;

    @BeforeEach
    void setUp() {
        List<ArgumentsInitializer> initializers =
                List.of(new SortArgumentInitializer(),
                        new OrderArgumentInitializer(),
                        new PathArgumentInitializer(),
                        new StatisticsIsPresentInitializer(),
                        new StatisticsOutputTypeInitializer());
        argumentsReader = new ArgumentsReader(initializers);
    }

    @Test
    void readArgumentsWithAllArgs() {
        // given
        AppArguments expectedAppArguments =
                new AppArguments(
                        SortType.NAME,
                        OrderType.ASC,
                        new StatisticsConfig(true, OutputType.FILE, "output/tmp/outputfile.txt"));
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
        AppArguments expectedAppArguments =
                new AppArguments(
                        SortType.SALARY, OrderType.DESC, new StatisticsConfig(true, null, null));
        String[] args = {
            "--stat", "--sort=salary", "--order=desc",
        };

        // when
        AppArguments appArguments = argumentsReader.readArguments(args);

        // then
        assertThat(expectedAppArguments).isEqualTo(appArguments);
    }
}

package ru.korona.task.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class FileServiceTest {
    private FileService fileService;
    private Path output;

    @BeforeEach
    void setUp() {
        fileService = new FileService();
        output = Path.of("output");
    }

    @AfterEach
    void deleteAllFiles() throws Exception {
        if (Files.exists(output)) {
            Files.walk(output)
                    .sorted(Comparator.reverseOrder())
                    .forEach(path -> {
                        try {
                            Files.deleteIfExists(path);
                        } catch (IOException e) {
                            throw new UncheckedIOException(
                                    "Error while deleting: "
                                            + path
                                            + ". Exception: ", e);
                        }
                    });
        }
    }

    @Test
    void storeData() throws Exception {
        // given
        List<String> data =
                List.of(
                        "Manager, 1, Jane Smith, 5000,00",
                        "Employee, 101, John Doe, 3000,00, 1",
                        "Employee, 105, Claire Green, 2900,75, 1");
        Path outputTestPath = Path.of(output.toString(), "test");
        String fileName = "HR";

        // when
        fileService.storeData(data, outputTestPath.toString(), fileName);

        // then
        Path expectedFile = Path.of(outputTestPath.toString(), fileName);
        assertThat(Files.exists(expectedFile)).isTrue();
        assertThat(Files.readAllLines(expectedFile)).isEqualTo(data);
    }
}

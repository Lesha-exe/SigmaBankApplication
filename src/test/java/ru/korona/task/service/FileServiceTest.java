package ru.korona.task.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@Slf4j
public class FileServiceTest {
    private FileService fileService;

    @BeforeEach
    void setUp() throws IOException {
        fileService = new FileService();
    }

    @Test
    void storeData() throws Exception {
        // given
        List<String> data =
                List.of(
                        "Manager, 1, Jane Smith, 5000,00",
                        "Employee, 101, John Doe, 3000,00, 1",
                        "Employee, 105, Claire Green, 2900,75, 1");
        Path outputPath = Path.of("testOutputDirectory");
        String fileName = "HR";

        // when
        fileService.storeData(data, outputPath.toString(), fileName);

        // then
        Path expectedFile = Path.of(outputPath.toString(), fileName);
        assertThat(Files.exists(expectedFile)).isTrue();
        List<String> lines = Files.readAllLines(expectedFile);
        assertThat(lines).isEqualTo(data);
    }
}

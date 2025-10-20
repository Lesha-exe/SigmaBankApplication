package ru.korona.task.service.reader;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.korona.task.service.FileService;

@Slf4j
public class FileServiceTest {
    private FileService fileService;
    private Path temporaryDirectory;

    @BeforeEach
    void setUp() {
        fileService = new FileService();
        try {
            temporaryDirectory = Files.createTempDirectory("testOutputDirectory");
        } catch (IOException exception) {
            log.error(
                    "Cannot create a path: "
                            + "testOutputDirectory"
                            + ". Exception: "
                            + exception.getMessage());
        }
    }

    @AfterEach
    void deleteTemporaryDirectory() {
        if (Files.exists(temporaryDirectory)) {
            try {
                Files.walk(temporaryDirectory)
                        .sorted(Comparator.reverseOrder())
                        .forEach(
                                path -> {
                                    try {
                                        Files.deleteIfExists(path);
                                    } catch (IOException exception) {
                                        log.error(
                                                "Cannot delete files from the path: "
                                                        + path
                                                        + "Exception: "
                                                        + exception.getMessage());
                                    }
                                });
            } catch (IOException exception) {
                log.error(
                        "Cannot get files and directory stream  from: "
                                + temporaryDirectory
                                + " path. Or walk through it: "
                                + ". Exception: "
                                + exception.getMessage());
            }
        }
    }

    @Test
    void storeData() {
        // given
        List<String> data =
                List.of(
                        "Manager, 1, Jane Smith, 5000,00",
                        "Employee, 101, John Doe, 3000,00, 1",
                        "Employee, 105, Claire Green, 2900,75, 1");
        String fileName = "HR";

        // when
        fileService.storeData(data, temporaryDirectory.toString(), fileName);

        // then
        Path expectedFile = Path.of(temporaryDirectory.toString(), fileName);
        assertThat(Files.exists(expectedFile)).isTrue();
        try {
            List<String> lines = Files.readAllLines(expectedFile);
            assertThat(lines).isEqualTo(data);
        } catch (IOException exception) {
            log.error(
                    "Cannot read lines from filePath: "
                            + expectedFile
                            + " .Exception: "
                            + exception.getMessage());
        }
    }
}

package ru.korona.task.service;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.korona.task.models.Worker;

@Component
@Slf4j
public class InvalidDataService {
    private final String outputDirectory;

    public InvalidDataService(@Value("${departments.outputDir}") String outputDirectory) {
        this.outputDirectory = outputDirectory;
    }

    public void storeInvalidData(List<Worker> workersWithIncorrectData) {
        Path path = Path.of(outputDirectory, "error.log");
        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path)) {
            for (Worker worker : workersWithIncorrectData) {
                Files.createDirectories(path.getParent());
                bufferedWriter.write(String.valueOf(worker));
                bufferedWriter.newLine();
            }
        } catch (IOException exception) {
            log.info(
                    "Cannot write invalid workers data to file: "
                            + path.getFileName()
                            + "Exception: "
                            + exception.getMessage());
        }
    }
}

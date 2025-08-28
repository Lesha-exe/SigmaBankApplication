package ru.korona.task.services;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.korona.task.models.Worker;

@Component
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
            System.out.println("Cannot write invalid workers data to file: " + path.getFileName());
        }
    }
}

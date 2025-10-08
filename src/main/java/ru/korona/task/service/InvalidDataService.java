package ru.korona.task.service;

import java.nio.file.Path;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.korona.task.models.Worker;

@Component
@Slf4j
public class InvalidDataService {
    private final FileService fileService;
    private final String outputDirectory;
    private final String outputFileName;

    public InvalidDataService(
            @Value("${invalidData.outputFileName}") String outputFileName,
            @Value("${departments.outputDir}") String outputDirectory,
            FileService fileService) {
        this.fileService = fileService;
        this.outputFileName = outputFileName;
        this.outputDirectory = outputDirectory;
    }

    public void storeInvalidData(List<Worker> workersWithIncorrectData) {
        Path path = Path.of(outputDirectory, outputFileName);
        List<String> invalidData = workersWithIncorrectData.stream().map(String::valueOf).toList();
        fileService.storeData(invalidData, outputDirectory, outputFileName);
    }
}

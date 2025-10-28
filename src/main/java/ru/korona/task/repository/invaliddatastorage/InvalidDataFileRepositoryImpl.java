package ru.korona.task.repository.invaliddatastorage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import ru.korona.task.models.Worker;
import ru.korona.task.repository.InvalidDataRepository;
import ru.korona.task.service.FileService;

import java.util.List;

@Component
@Profile("file")
public class InvalidDataFileRepositoryImpl implements InvalidDataRepository {
    private final FileService fileService;
    private final String outputDirectory;
    private final String outputFileName;

    public InvalidDataFileRepositoryImpl(
            @Value("${invalidData.outputFileName}") String outputFileName,
            @Value("${departments.outputDir}") String outputDirectory,
            FileService fileService) {
        this.fileService = fileService;
        this.outputFileName = outputFileName;
        this.outputDirectory = outputDirectory;
    }

    @Override
    public void storeData(List<Worker> workersWithIncorrectData) {
        List<String> invalidData = workersWithIncorrectData.stream().map(String::valueOf).toList();
        fileService.storeData(invalidData, outputDirectory, outputFileName);
    }
}

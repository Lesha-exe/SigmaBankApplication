package ru.korona.task.services;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.korona.task.models.AbstractWorker;
import ru.korona.task.models.Worker;
import ru.korona.task.models.WorkerWithIncorrectData;

@Component
public class CsvWorkerDataReader implements WorkerDataReader {
    private WorkerDataParser workerDataParser;
    private String inputDirectory;
    private Path filePath = FileSystems.getDefault().getPath(inputDirectory, "file1.sb");

    public CsvWorkerDataReader(@Value("${dataReader.inputDir}") String inputDirectory) {
        this.inputDirectory = inputDirectory;
    }

    @Override
    public WorkerData readWorkers() {
        final List<String[]> fileEntries = getFileEntries();
        List<Worker> workers =
                fileEntries.stream().map(fileEntry -> workerDataParser.parse(fileEntry)).toList();
        return buildWorkerData(workers);
    }

    public List<String[]> getFileEntries() {
        final List<String[]> fileEntries = new ArrayList<>();
        try (Reader reader = Files.newBufferedReader(filePath);
                CSVReader csvReader = new CSVReader(reader)) {
            String[] line;
            while ((line = csvReader.readNext()) != null) {
                fileEntries.add(line);
            }
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        } catch (CsvValidationException exception) {
            throw new RuntimeException(exception);
        }
        return fileEntries;
    }

    private static WorkerData buildWorkerData(List<Worker> workers) {
        List<Worker> workersWithCorrectData =
                workers.stream().filter(worker -> worker instanceof AbstractWorker).toList();
        List<Worker> workersWithIncorrectData =
                workers.stream()
                        .filter(worker -> worker instanceof WorkerWithIncorrectData)
                        .toList();
        return new WorkerData(workersWithCorrectData, workersWithIncorrectData);
    }
}

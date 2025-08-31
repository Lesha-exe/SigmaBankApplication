package ru.korona.task.service.reader;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.korona.task.models.*;
import ru.korona.task.service.parser.WorkerDataParser;

@Component
public class CsvWorkerDataReader implements WorkerDataReader {
    private final WorkerDataParser workerDataParser;
    private String inputDirectory;

    public CsvWorkerDataReader(
            WorkerDataParser workerDataParser,
            @Value("${dataReader.inputDir}") String inputDirectory) {
        this.workerDataParser = workerDataParser;
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
        try (Stream<Path> paths = Files.list(Paths.get(inputDirectory))) {
            List<Path> files = paths.filter(Files::isRegularFile).collect(Collectors.toList());

            files.forEach(
                    filePath -> {
                        fileEntries.addAll(getFileEntriesFromFile(filePath));
                    });
            return fileEntries;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static List<String[]> getFileEntriesFromFile(Path filePath) {
        final List<String[]> fileEntries = new ArrayList<>();
        try (Reader reader = Files.newBufferedReader(filePath);
                CSVReader csvReader = new CSVReader(reader)) {
            String[] line;
            while ((line = csvReader.readNext()) != null) {
                fileEntries.add(line);
            }
            return fileEntries;
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        } catch (CsvValidationException exception) {
            throw new RuntimeException(exception);
        }
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

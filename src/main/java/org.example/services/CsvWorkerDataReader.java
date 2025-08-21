package org.example.services;

import com.opencsv.CSVReader;
import org.example.models.AbstractWorker;
import org.example.models.Worker;
import org.example.models.WorkerWithIncorrectData;
import org.w3c.dom.css.CSSValue;

import java.io.Reader;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class CsvWorkerDataReader implements WorkerDataReader {
    private WorkerDataParser workerDataParser;
    private Path filePath = FileSystems.getDefault().getPath("input", "file1.sb");

    @Override
    public  WorkerData readWorkers() throws Exception {
        List<String[]> fileEntries = new ArrayList<>();
        try (Reader reader = Files.newBufferedReader(filePath)) {
            try (CSVReader csvReader = new CSVReader(reader)) {
                String[] line;
                while ((line = csvReader.readNext()) != null) {
                    fileEntries.add(line);
                }
            }
        }
        List<Worker> workers = fileEntries.stream()
                .map(fileEntry -> workerDataParser
                        .parse(fileEntry)).toList();
        List<Worker> workersWithCorrectData = workers.stream()
                .filter(worker -> worker instanceof AbstractWorker)
                .toList();
        List<Worker> workersWithIncorrectData = workers.stream()
                .filter(worker -> worker instanceof WorkerWithIncorrectData)
                .toList();
        return new WorkerData(workersWithCorrectData, workersWithIncorrectData);
    }
}

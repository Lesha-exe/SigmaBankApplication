package ru.korona.task.service;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class FileService {

    public void storeData(List<String> data, Path filePath) {
        try (BufferedWriter writer = Files.newBufferedWriter(filePath)) {
            for(String dataLine: data){
                writeDataToFile(dataLine, writer);
            }
        } catch (Exception exception) {
            log.info(
                    "Error while saving data to directory: "
                            + filePath
                            + "Exception: "
                            + exception);
        }
    }

    private static void writeDataToFile(String data, BufferedWriter writer) {
        try {
            writer.write(data);
            writer.newLine();
        } catch (Exception exception) {
            log.info("Error while writing file: " + exception.getMessage());
        }
    }
}


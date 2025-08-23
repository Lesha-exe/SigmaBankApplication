package org.example.services;

import lombok.AllArgsConstructor;
import org.example.models.Worker;
import org.example.models.WorkerWithIncorrectData;
import org.example.outputSettings.WorkerType;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@AllArgsConstructor
public class WorkerDataParser {
    private Map<WorkerType, WorkerParser> parsers;

    public Worker parse(String[] workerData) {
        WorkerType workerType = WorkerType.from(workerData[0]);
        WorkerParser workerParser = parsers.get(workerType);
        if (workerParser == null) {
            return new WorkerWithIncorrectData(workerData);
        }
        return workerParser.parse(workerData);
    }
}

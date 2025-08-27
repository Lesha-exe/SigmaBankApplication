package ru.korona.task.services;

import java.util.Map;
import lombok.AllArgsConstructor;
import ru.korona.task.models.Worker;
import ru.korona.task.models.WorkerWithIncorrectData;
import ru.korona.task.outputSettings.WorkerType;
import org.springframework.stereotype.Component;

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

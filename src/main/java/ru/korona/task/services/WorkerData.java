package ru.korona.task.services;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.korona.task.models.Worker;

@AllArgsConstructor
@Getter
public class WorkerData {
    List<Worker> workersWithCorrectData;
    List<Worker> WorkersWithIncorrectData;
}

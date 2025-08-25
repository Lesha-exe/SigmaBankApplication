package org.example.services;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.models.Worker;

@AllArgsConstructor
@Getter
public class WorkerData {
    List<Worker> workersWithCorrectData;
    List<Worker> WorkersWithIncorrectData;
}

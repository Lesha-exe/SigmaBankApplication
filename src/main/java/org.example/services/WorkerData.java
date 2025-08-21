package org.example.services;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.models.Worker;

import java.util.List;

@AllArgsConstructor
@Getter
public class WorkerData {
    List<Worker> workersWithCorrectData;
    List<Worker> WorkersWithIncorrectData;
}

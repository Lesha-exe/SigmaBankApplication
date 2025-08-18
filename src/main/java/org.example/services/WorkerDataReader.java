package org.example.services;

import org.example.models.Worker;

import java.util.List;

public interface WorkerDataReader {
    List<Worker> readWorkers();
}

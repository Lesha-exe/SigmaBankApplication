package org.example.services;

import org.example.models.Worker;

import java.util.List;

public interface WorkerDataReader {
    WorkerData readWorkers() throws Exception;
}

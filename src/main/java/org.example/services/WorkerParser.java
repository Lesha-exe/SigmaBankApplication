package org.example.services;

import org.example.models.Worker;

public interface WorkerParser {
    Worker parse(String[] workerData);
}
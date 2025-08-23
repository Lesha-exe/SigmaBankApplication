package org.example;

import lombok.RequiredArgsConstructor;
import org.example.models.AppArguments;
import org.example.services.ArgumentsReader;
import org.example.services.WorkerData;
import org.example.services.WorkerDataReader;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WorkerDataApplication {
    private final ArgumentsReader argumentsReader;
    private final WorkerDataReader workerDataReader;

    public void run(String[] args) {
        try {
            AppArguments appArguments = argumentsReader.readArguments(args);
            WorkerData workerData = workerDataReader.readWorkers();
        } catch (Exception exception){
            System.out.println(exception.getMessage());
        }
    }
    }

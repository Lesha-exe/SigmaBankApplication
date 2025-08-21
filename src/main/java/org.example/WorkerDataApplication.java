package org.example;

import lombok.RequiredArgsConstructor;
import org.example.services.ArgumentsReader;
import org.example.services.WorkerDataReader;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WorkerDataApplication {
    private final ArgumentsReader argumentsReader;
    private final WorkerDataReader workerDataReader;

}

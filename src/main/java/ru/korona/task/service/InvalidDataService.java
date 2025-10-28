package ru.korona.task.service;

import java.nio.file.Path;
import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.korona.task.models.Worker;
import ru.korona.task.repository.InvalidDataRepository;

@Component
@Slf4j
@RequiredArgsConstructor
public class InvalidDataService {
    private final InvalidDataRepository invalidDataRepository;

    public void storeInvalidData(List<Worker> workersWithIncorrectData) {
        invalidDataRepository.storeData(workersWithIncorrectData);
    }
}

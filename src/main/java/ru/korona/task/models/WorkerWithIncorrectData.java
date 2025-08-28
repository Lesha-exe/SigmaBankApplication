package ru.korona.task.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class WorkerWithIncorrectData implements Worker {
    private String[] data;

    public String toString() {
        return String.join(", ", data);
    }
}

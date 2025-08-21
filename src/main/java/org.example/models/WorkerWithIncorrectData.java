package org.example.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class WorkerWithIncorrectData implements Worker {
    private String[] data;
}

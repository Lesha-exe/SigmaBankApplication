package ru.korona.task.service.storagedata;

import java.util.List;

public class InvalidDataStorage implements FileStorage {
    @Override
    public void storeData(List<String> data) {}
}

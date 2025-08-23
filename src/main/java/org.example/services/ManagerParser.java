package org.example.services;

import org.example.models.Manager;
import org.example.models.Worker;

public class ManagerParser implements WorkerParser{
    @Override
    public Worker parse(String[] workerData) {
        Manager.ManagerBuilder managerBuilder = Manager.builder();
        managerBuilder.id(Integer.parseInt(workerData[1]));
        managerBuilder.name(workerData[2]);
        managerBuilder.salary(Integer.parseInt(workerData[3]));
        managerBuilder.department(workerData[4]);
        return managerBuilder.build();
    }
}

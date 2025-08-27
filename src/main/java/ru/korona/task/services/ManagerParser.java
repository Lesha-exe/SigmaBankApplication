package ru.korona.task.services;

import ru.korona.task.models.Manager;
import ru.korona.task.models.Worker;
import ru.korona.task.models.WorkerWithIncorrectData;

public class ManagerParser implements WorkerParser {
    @Override
    public Worker parse(String[] workerData) {
        Manager.ManagerBuilder managerBuilder = Manager.builder();
        try {
            managerBuilder.id(Integer.parseInt(workerData[1]));
            managerBuilder.salary(Integer.parseInt(workerData[3]));
        } catch (NumberFormatException exception) {
            return new WorkerWithIncorrectData(workerData);
        }
        managerBuilder.name(workerData[2]);
        managerBuilder.department(workerData[4]);
        Manager manager = managerBuilder.build();
        if (!isValid(manager)) {
            return new WorkerWithIncorrectData(workerData);
        }
        return manager;
    }

    private boolean isValid(Manager manager) {
        if (manager.getId() < 0 || manager.getId().equals(null)) {
            return false;
        } else if (manager.getSalary() < 0 || manager.getSalary().equals(null)) {
            return false;
        } else if (manager.getName().equals(null)) {
            return false;
        } else if (manager.getDepartment().equals(null)) {
            return false;
        }
        return true;
    }
}

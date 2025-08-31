package ru.korona.task.service.parser;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import ru.korona.task.models.Manager;
import ru.korona.task.models.Worker;
import ru.korona.task.models.WorkerWithIncorrectData;

@Component
public class ManagerParser implements WorkerParser {
    @Override
    public Worker parse(String[] workerData) {
        Manager.ManagerBuilder managerBuilder = Manager.builder();
        try {
            managerBuilder.id(Integer.parseInt(workerData[1]));
            managerBuilder.salary(Double.parseDouble(workerData[3]));
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
        return manager.getId() > 0
                && manager.getSalary() > 0
                && StringUtils.isNotBlank(manager.getName())
                && StringUtils.isNotBlank(manager.getDepartment());
    }
}

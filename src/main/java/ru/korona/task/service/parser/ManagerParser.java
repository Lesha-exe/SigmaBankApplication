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
            managerBuilder.id(Integer.parseInt(workerData[1].trim()));
            managerBuilder.salary(Double.parseDouble(workerData[3].trim()));
        } catch (NumberFormatException exception) {
            return new WorkerWithIncorrectData(workerData);
        }
        managerBuilder.name(workerData[2].trim());
        managerBuilder.department(workerData[4].trim());
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

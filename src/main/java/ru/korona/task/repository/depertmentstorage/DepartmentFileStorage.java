package ru.korona.task.repository.depertmentstorage;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import ru.korona.task.models.Department;
import ru.korona.task.repository.DepartmentRepository;

import java.util.List;

@Component
@Profile("file")
public class DepartmentFileStorage implements DepartmentRepository {
    @Override
    public void storeData(List<Department> departmentsList) {
    }
}

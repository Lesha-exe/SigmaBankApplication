package ru.korona.task.repository;

import java.util.List;
import ru.korona.task.models.Department;

public interface DepartmentRepository {
    void storeData(List<Department> departmentsList);
}

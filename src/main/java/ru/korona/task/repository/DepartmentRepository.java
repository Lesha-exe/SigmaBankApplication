package ru.korona.task.repository;

import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import ru.korona.task.models.Department;

import java.util.List;

public interface DepartmentRepository {
    void storeData(List<Department> departmentsList);
}

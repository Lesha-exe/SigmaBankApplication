package ru.korona.task.repository.depertmentstorage;

import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.korona.task.models.Department;
import ru.korona.task.models.Employee;
import ru.korona.task.models.Manager;
import ru.korona.task.repository.DepartmentRepository;

import java.util.List;

@Component
@Profile("JdbcTemplate")
public class DepartmentJdbcTemplateRepositoryImpl implements DepartmentRepository {
    private final JdbcTemplate jdbcTemplate;

    public DepartmentJdbcTemplateRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    @Transactional
    public void storeData(List<Department> departmentsList) {
        for (Department department : departmentsList) {
            storeManager(department.getManager());
            storeEmployees(department.getEmployeeList());
        }
    }

    private void storeManager(Manager manager) {
        jdbcTemplate.update(
                insertManagersData(),
                manager.getId(),
                manager.getName(),
                manager.getSalary(),
                manager.getDepartment()
        );
    }

    private void storeEmployees(List<Employee> employees) {
        jdbcTemplate.batchUpdate(
                insertEmployeesData(),
                employees,
                employees.size(),
                (ps, employee) -> {
                    ps.setLong(1, employee.getId());
                    ps.setString(2, employee.getName());
                    ps.setDouble(3, employee.getSalary());
                    ps.setLong(4, employee.getManagerId());
                }
        );
    }

    private String insertManagersData() {
        return """
                INSERT INTO managers (id, name, salary, department, creation_timestamp)
                VALUES (?, ?, ?, ?, NOW())
                ON CONFLICT (id) DO UPDATE
                    SET name = EXCLUDED.name,
                        salary = EXCLUDED.salary,
                        department = EXCLUDED.department
                """;
    }

    private String insertEmployeesData() {
        return """
                INSERT INTO employees (id, name, salary, manager_id, creation_timestamp)
                VALUES (?, ?, ?, ?, NOW())
                ON CONFLICT (id) DO UPDATE
                    SET name = EXCLUDED.name,
                        salary = EXCLUDED.salary,
                        manager_id = EXCLUDED.manager_id
                """;
    }
}

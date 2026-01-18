package ru.korona.task.repository.depertmentstorage;

import java.sql.*;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import ru.korona.task.models.Department;
import ru.korona.task.models.Employee;
import ru.korona.task.models.Manager;
import ru.korona.task.repository.DepartmentRepository;

@Component
@Profile("JdbcConnection")
@Slf4j
public class DepartmentJdbcConnectionRepositoryImpl implements DepartmentRepository {
    private final String url;
    private final String username;
    private final String password;

    public DepartmentJdbcConnectionRepositoryImpl(
            @Value("${spring.datasource.url}") String url,
            @Value("${spring.datasource.username}") String username,
            @Value("${spring.datasource.password}") String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    @Override
    public void storeData(List<Department> departmentsList) {
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            for (Department department : departmentsList) {
                try {
                    connection.setAutoCommit(false);
                    storeManger(department.getManager(), connection);
                    storeEmployees(department.getEmployeeList(), connection);
                    connection.commit();
                } catch (SQLException exception) {
                    log.error(
                            "Error while saving department: {}",
                            department.getManager().getDepartment(),
                            exception);
                    try {
                        connection.rollback();
                        log.warn(
                                "Transaction for department {} rolled back",
                                department.getManager().getDepartment(),
                                exception);
                    } catch (SQLException rollbackException) {
                        log.error("Rollback failure", rollbackException);
                    }
                } finally {
                    connection.setAutoCommit(true);
                }
            }
        } catch (SQLException exception) {
            log.error("Failed to store department data", exception);
        }
    }

    private void storeManger(Manager manager, Connection connection) {
        try (PreparedStatement stmt = connection.prepareStatement(insertManagersDataQuery())) {
            stmt.setLong(1, manager.getId());
            stmt.setString(2, manager.getName());
            stmt.setDouble(3, manager.getSalary());
            stmt.setString(4, manager.getDepartment());
            stmt.executeUpdate();
        } catch (SQLException exception) {
            log.error(
                    "Cannot save manager: id=[{}], name=[{}]",
                    manager.getId(),
                    manager.getName(),
                    exception);
        }
    }

    private void storeEmployees(List<Employee> employees, Connection connection) {
        try (PreparedStatement stmt = connection.prepareStatement(insertEmployeesDataQuery())) {
            for (Employee e : employees) {
                stmt.setLong(1, e.getId());
                stmt.setString(2, e.getName());
                stmt.setDouble(3, e.getSalary());
                stmt.setLong(4, e.getManagerId());
                stmt.addBatch();
            }
            stmt.executeBatch();
        } catch (SQLException exception) {
            log.error("Cannot save employee list: employee=[{}]", employees.toString(), exception);
        }
    }

    private String insertManagersDataQuery() {
        return """
                INSERT INTO managers (id, name, salary, department, creation_timestamp)
                VALUES (?, ?, ?, ?, NOW())
                ON CONFLICT (id) DO UPDATE
                    SET name = EXCLUDED.name,
                        salary = EXCLUDED.salary,
                        department = EXCLUDED.department
                """;
    }

    private String insertEmployeesDataQuery() {
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

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
@Profile("database")
@Slf4j
public class DepartmentDataBaseRepositoryImpl implements DepartmentRepository {
    private final String url;
    private final String username;
    private final String password;

    public DepartmentDataBaseRepositoryImpl(
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
            createTablesIfNotExist(connection);
            for (Department department : departmentsList) {
                storeManger(department.getManager(), connection);
                storeEmployees(department.getEmployeeList(), connection);
            }
        } catch (SQLException exception) {
            log.error("Cannot connect to data base! Exception: " + exception.getMessage());
        }
    }

    private void storeManger(Manager manager, Connection connection) {
        try (PreparedStatement stmt = connection.prepareStatement(insertManagersDataRequest())) {
            stmt.setLong(1, manager.getId());
            stmt.setString(2, manager.getName());
            stmt.setDouble(3, manager.getSalary());
            stmt.setString(4, manager.getDepartment());
            stmt.executeUpdate();
        } catch (SQLException exception) {
            log.error(
                    "Cannot save manager: id("
                            + manager.getId()
                            + "), name("
                            + manager.getName()
                            + "). Exception: "
                            + exception.getMessage());
        }
    }

    private void storeEmployees(List<Employee> employees, Connection connection) {
        try (PreparedStatement stmt = connection.prepareStatement(insertEmployeesDataRequest())) {
            for (Employee e : employees) {
                stmt.setLong(1, e.getId());
                stmt.setString(2, e.getName());
                stmt.setDouble(3, e.getSalary());
                stmt.setLong(4, e.getManagerId());
                stmt.addBatch();
            }
            stmt.executeBatch();
        } catch (SQLException exception) {
            log.error(
                    "Cannot save employee list: "
                            + employees.toString()
                            + ". Exception: "
                            + exception.getMessage());
        }
    }

    private void createTablesIfNotExist(Connection connection) {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(createManagersTableRequest());
            stmt.execute(createEmployeesTableRequest());
        } catch (SQLException exception) {
            log.error(
                    "Cannot send table creation request! "
                            + "Table name(s): managers/employees. "
                            + "Exception: "
                            + exception.getMessage());
        }
    }

    private String createManagersTableRequest() {
        return """
                    CREATE TABLE IF NOT EXISTS managers (
                        id BIGINT PRIMARY KEY,
                        name VARCHAR(255) NOT NULL,
                        salary DOUBLE PRECISION,
                        department VARCHAR(255)
                    );
                """;
    }

    private String createEmployeesTableRequest() {
        return """
                    CREATE TABLE IF NOT EXISTS employees (
                        id BIGINT PRIMARY KEY,
                        name VARCHAR(255) NOT NULL,
                        salary DOUBLE PRECISION,
                        manager_id BIGINT REFERENCES managers(id)
                    );
                """;
    }

    private String insertManagersDataRequest() {
        return """
                INSERT INTO managers (id, name, salary, department)
                VALUES (?, ?, ?, ?)
                ON CONFLICT (id) DO UPDATE
                    SET name = EXCLUDED.name,
                        salary = EXCLUDED.salary,
                        department = EXCLUDED.department
                """;
    }

    private String insertEmployeesDataRequest() {
        return """
                INSERT INTO employees (id, name, salary, manager_id)
                VALUES (?, ?, ?, ?)
                ON CONFLICT (id) DO UPDATE
                    SET name = EXCLUDED.name,
                        salary = EXCLUDED.salary,
                        manager_id = EXCLUDED.manager_id
                """;
    }
}

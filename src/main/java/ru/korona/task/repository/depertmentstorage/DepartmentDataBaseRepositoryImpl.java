package ru.korona.task.repository.depertmentstorage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import ru.korona.task.models.Department;
import ru.korona.task.models.Employee;
import ru.korona.task.models.Manager;
import ru.korona.task.repository.DepartmentRepository;

import java.sql.*;
import java.util.List;

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
        try (Connection connection = DriverManager.getConnection(url, username, password)){
            createTablesIfNotExist(connection);
            for(Department department : departmentsList){
                storeManger(department.getManager(), connection);
                storeEmployees(department.getEmployeeList(), connection);
            }
        } catch (SQLException exception){
            log.error("Cannot connect to data base! Exception: " + exception.getMessage());
        }
    }

    private void storeManger(Manager manager, Connection connection){
        String sql = """
                INSERT INTO managers (id, name, salary, department)
                VALUES (?, ?, ?, ?)
                ON CONFLICT (id) DO UPDATE
                    SET name = EXCLUDED.name,
                        salary = EXCLUDED.salary,
                        department = EXCLUDED.department
                """;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, manager.getId());
            stmt.setString(2, manager.getName());
            stmt.setDouble(3, manager.getSalary());
            stmt.setString(4, manager.getDepartment());
            stmt.executeUpdate();
        } catch (SQLException exception){
            log.error("Cannot save manager: id("
                    + manager.getId()
                    + "), name("
                    + manager.getName()
                    + "). Exception: "
                    + exception.getMessage());
        }
    }

    private void storeEmployees(List<Employee> employees, Connection conn) {
        String sql = """
                INSERT INTO employees (id, name, salary, manager_id)
                VALUES (?, ?, ?, ?)
                ON CONFLICT (id) DO UPDATE
                    SET name = EXCLUDED.name,
                        salary = EXCLUDED.salary,
                        manager_id = EXCLUDED.manager_id
                """;

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            for (Employee e : employees) {
                stmt.setLong(1, e.getId());
                stmt.setString(2, e.getName());
                stmt.setDouble(3, e.getSalary());
                stmt.setLong(4, e.getManagerId());
                stmt.addBatch();
            }
            stmt.executeBatch();
        } catch (SQLException exception) {
            log.error("Cannot save employee list: "
                    + employees.toString()
                    + ". Exception: "
                    + exception.getMessage());
        }
    }

    private void createTablesIfNotExist(Connection connection) throws SQLException {
        String createManagersTable = """
        CREATE TABLE IF NOT EXISTS managers (
            id BIGINT PRIMARY KEY,
            name VARCHAR(255) NOT NULL,
            salary DOUBLE PRECISION,
            department VARCHAR(255)
        );
    """;

        String createEmployeesTable = """
        CREATE TABLE IF NOT EXISTS employees (
            id BIGINT PRIMARY KEY,
            name VARCHAR(255) NOT NULL,
            salary DOUBLE PRECISION,
            manager_id BIGINT REFERENCES managers(id)
        );
    """;

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(createManagersTable);
            stmt.execute(createEmployeesTable);
        }
    }
}

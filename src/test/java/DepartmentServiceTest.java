import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.korona.task.models.*;
import ru.korona.task.objectparameters.OrderType;
import ru.korona.task.objectparameters.SortType;
import ru.korona.task.service.DepartmentService;
import ru.korona.task.service.FileService;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

public class DepartmentServiceTest {
    private DepartmentService departmentService;
    private FileService fileService;

    @BeforeEach
    void setUp() {
        fileService = Mockito.mock(FileService.class);
        departmentService = new DepartmentService(
                "output/",
                ".txt",
                fileService
        );
    }

    @Test
    void createDepartments() {
        List<Worker> workers = createWorkerList();
        AppArguments args = createAppArgumentsObject(SortType.SALARY, OrderType.ASC);

        List<Department> departments = departmentService.createDepartments(workers, args);

        assertThat(departments).hasSize(2);
        Department HR = departments.get(0);
        assertThat(HR.getManager().getName()).isEqualTo("Manager 1");
        assertThat(HR.getEmployeeList()).extracting(Employee::getName)
                .containsExactly("Employee 2", "Employee 1");

        Department IT = departments.get(1);
        assertThat(IT.getManager().getName()).isEqualTo("Manager 2");
        assertThat(IT.getEmployeeList()).extracting(Employee::getName)
                .containsExactly("Employee 3");
    }

    private List<Worker> createWorkerList(){
        List<Worker> workerList = new ArrayList<>();
        Manager manager1 = createManager(1, "Manager 1", 2000.0, "HR");
        Manager manager2 = createManager(2, "Manager 2", 3000.0, "IT");
        Employee employee1 = createEmployee(11, "Employee 1", 1500.0, 1);
        Employee employee2 = createEmployee(12, "Employee 2", 1400.0, 1);
        Employee employee3 = createEmployee(13, "Employee 3", 2000.0, 2);
        return List.of(manager1, employee1, employee2, manager2, employee3);
    }

    private Manager createManager(Integer id, String name,
                                  Double salary, String department){
        Manager.ManagerBuilder managerBuilder = Manager.builder();
        managerBuilder.id(id);
        managerBuilder.name(name);
        managerBuilder.salary(salary);
        managerBuilder.department(department);
        return managerBuilder.build();
    }

    private Employee createEmployee(Integer id, String name,
                                    Double salary, Integer managerId){
        Employee.EmployeeBuilder employeeBuilder = Employee.builder();
        employeeBuilder.id(id);
        employeeBuilder.name(name);
        employeeBuilder.salary(salary);
        employeeBuilder.managerId(managerId);
        return employeeBuilder.build();
    }

    private AppArguments createAppArgumentsObject(SortType sortType, OrderType orderType){
        AppArguments.AppArgumentsBuilder appArgumentsBuilder = AppArguments.builder();
        appArgumentsBuilder.sortType(sortType);
        appArgumentsBuilder.order(orderType);
        return appArgumentsBuilder.build();
    }

}

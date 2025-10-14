import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import ru.korona.task.models.Manager;
import ru.korona.task.models.Employee;
import ru.korona.task.models.Worker;
import ru.korona.task.models.AppArguments;
import ru.korona.task.models.Department;
import ru.korona.task.objectparameters.OrderType;
import ru.korona.task.objectparameters.SortType;
import ru.korona.task.service.DepartmentService;
import ru.korona.task.service.FileService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

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
        // given
        List<Worker> workers = createWorkerList();
        AppArguments args = createAppArgumentsObject(SortType.SALARY, OrderType.ASC);

        // when
        List<Department> departments = departmentService.createDepartments(workers, args);

        // then
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

    @Test
    void createDepartmentWithoutEmployees(){
        // given
        Manager manager = createManager(1, "Manager 1", 2000.0, "HR");
        List<Worker> workers = List.of(manager);
        AppArguments args = createAppArgumentsObject(SortType.SALARY, OrderType.ASC);

        // when
        List<Department> department = departmentService.createDepartments(workers, args);

        // then
        assertThat(department).hasSize(1);
        Department IT = department.get(0);
        assertThat(IT.getManager().getName()).isEqualTo("Manager 1");
        assertThat(IT.getEmployeeList()).isNullOrEmpty();
    }

    @Test
    void storeDepartments(){
        // given
        List<Worker> workers = createWorkerList();
        AppArguments args = createAppArgumentsObject(SortType.SALARY, OrderType.ASC);
        ArgumentCaptor<List<String>> dataCaptor = ArgumentCaptor.forClass(List.class);
        ArgumentCaptor<String> directoryCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> fileNameCaptor = ArgumentCaptor.forClass(String.class);

        // when
        List<Department> departments = departmentService.createDepartments(workers, args);
        departmentService.storeDepartments(departments);

        // then
        verify(fileService, times(2))
                .storeData(dataCaptor.capture(),
                        directoryCaptor.capture(),
                        fileNameCaptor.capture());

        List<String> data = dataCaptor.getValue();
        assertThat(directoryCaptor.getValue()).isEqualTo("output/");
        assertThat(fileNameCaptor.getValue()).isEqualTo("IT.txt");
    }

    private List<Worker> createWorkerList(){
        Manager manager1 = createManager(1, "Manager 1", 2000.0, "HR");
        Manager manager2 = createManager(2, "Manager 2", 3000.0, "IT");
        Employee employee1 = createEmployee(11, "Employee 1", 1500.0, 1);
        Employee employee2 = createEmployee(12, "Employee 2", 1400.0, 1);
        Employee employee3 = createEmployee(13, "Employee 3", 2000.0, 2);
        return List.of(manager1, employee1, employee2, manager2, employee3);
    }

    private Manager createManager(Integer id, String name,
                                  Double salary, String department){
        return Manager.builder()
                .id(id)
                .name(name)
                .salary(salary)
                .department(department)
                .build();
    }

    private Employee createEmployee(Integer id, String name,
                                    Double salary, Integer managerId){
        return Employee.builder()
                .id(id)
                .name(name)
                .salary(salary)
                .managerId(managerId)
                .build();
    }

    private AppArguments createAppArgumentsObject(SortType sortType, OrderType orderType){
        return AppArguments.builder()
                .sortType(sortType)
                .order(orderType)
                .build();
    }

}

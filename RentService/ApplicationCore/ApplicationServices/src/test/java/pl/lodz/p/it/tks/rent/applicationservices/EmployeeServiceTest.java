package pl.lodz.p.it.tks.rent.applicationservices;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.lodz.p.it.tks.rent.applicationports.adapters.driven.EmployeeRepositoryAdapter;
import pl.lodz.p.it.tks.rent.applicationports.exception.RepositoryAdapterException;
import pl.lodz.p.it.tks.rent.domainmodel.user.Employee;
import pl.lodz.p.it.tks.rent.repository.UserEntRepository;

import java.util.List;

public class EmployeeServiceTest {

    private static EmployeeService employeeService;
    private static EmployeeRepositoryAdapter employeeRepositoryAdapter;

    @BeforeEach
    public void beforeMethod() {
        employeeRepositoryAdapter = new EmployeeRepositoryAdapter(new UserEntRepository());
        employeeService = new EmployeeService(employeeRepositoryAdapter, employeeRepositoryAdapter, employeeRepositoryAdapter, employeeRepositoryAdapter, employeeRepositoryAdapter, employeeRepositoryAdapter);
    }

    @Test
    public void addEmployeeTest() throws RepositoryAdapterException {
        employeeService.add(Employee.builder().firstname("Jan").lastname("Employee").login("employee").build());
        Assertions.assertThrows(RepositoryAdapterException.class, () ->
                employeeService.add(Employee.builder().firstname("Jan").lastname("Employee").login("employee").build()));

        Assertions.assertEquals(employeeRepositoryAdapter.get("employee").getLogin(), "employee");
        Assertions.assertEquals(employeeRepositoryAdapter.get("employee").getLastname(), "Employee");
        Assertions.assertEquals(employeeRepositoryAdapter.get("employee").getFirstname(), "Jan");
    }

    @Test
    public void updateEmployeeTest() throws RepositoryAdapterException {
        employeeService.add(Employee.builder().firstname("Jan").lastname("Employee").login("employee").build());

        Employee old = employeeService.get("employee");
        old.setFirstname("T1");
        old.setLastname("T2");

        Employee update = employeeService.update(old);

        Assertions.assertEquals(update.getFirstname(), "T1");
        Assertions.assertEquals(update.getLastname(), "T2");

        Assertions.assertEquals(employeeRepositoryAdapter.get("employee").getFirstname(), "T1");
        Assertions.assertEquals(employeeRepositoryAdapter.get("employee").getLastname(), "T2");
    }

    @Test
    public void getAllEmployeeTest() throws RepositoryAdapterException {
        employeeService.add(Employee.builder().firstname("Jan").lastname("Employee").login("employee1").build());
        employeeService.add(Employee.builder().firstname("Jan").lastname("Employee").login("employee2").build());
        employeeService.add(Employee.builder().firstname("Jan").lastname("Employee").login("employee3").build());

        List<Employee> employeeList = employeeService.getAll();

        Assertions.assertEquals(employeeList.size(), 3);
        Assertions.assertEquals(employeeList.get(0).getLogin(), "employee1");
        Assertions.assertEquals(employeeList.get(1).getLogin(), "employee2");
        Assertions.assertEquals(employeeList.get(2).getLogin(), "employee3");

    }

    @Test
    public void getByUUIDEmployeeTest() throws RepositoryAdapterException {
        Employee a = employeeService.add(Employee.builder().firstname("Jan").lastname("Employee").login("employee1").build());

        Employee get = employeeService.get(a.getId());

        Assertions.assertEquals(get, a);
    }

    @Test
    public void getByLoginEmployeeTest() throws RepositoryAdapterException {
        Employee a = employeeService.add(Employee.builder().firstname("Jan").lastname("Employee").login("employee1").build());

        Employee get = employeeService.get(a.getLogin());

        Assertions.assertEquals(get, a);
    }
}

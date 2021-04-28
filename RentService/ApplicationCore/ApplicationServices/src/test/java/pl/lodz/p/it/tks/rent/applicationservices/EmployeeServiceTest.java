package pl.lodz.p.it.tks.rent.applicationservices;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pl.lodz.p.it.tks.rent.applicationports.adapters.driven.EmployeeRepositoryAdapter;
import pl.lodz.p.it.tks.rent.applicationports.exception.RepositoryAdapterException;
import pl.lodz.p.it.tks.rent.domainmodel.user.Employee;
import pl.lodz.p.it.tks.rent.repository.UserEntRepository;

import java.util.List;

public class EmployeeServiceTest {

    private EmployeeService employeeService;
    private EmployeeRepositoryAdapter employeeRepositoryAdapter;

    @BeforeMethod
    public void beforeMethod() {
        employeeRepositoryAdapter = new EmployeeRepositoryAdapter(new UserEntRepository());
        employeeService = new EmployeeService(employeeRepositoryAdapter, employeeRepositoryAdapter, employeeRepositoryAdapter, employeeRepositoryAdapter, employeeRepositoryAdapter);
    }

    @Test
    public void addEmployeeTest() throws RepositoryAdapterException {
        employeeService.add(Employee.builder().firstname("Jan").lastname("Employee").password("zaq1@WSX").login("employee").build());
        Assert.assertThrows(RepositoryAdapterException.class, () ->
                employeeService.add(Employee.builder().firstname("Jan").lastname("Employee").password("zaq1@WSX").login("employee").build()));

        Assert.assertEquals(employeeRepositoryAdapter.get("employee").getLogin(), "employee");
        Assert.assertEquals(employeeRepositoryAdapter.get("employee").getLastname(), "Employee");
        Assert.assertEquals(employeeRepositoryAdapter.get("employee").getFirstname(), "Jan");
        Assert.assertEquals(employeeRepositoryAdapter.get("employee").getPassword(), "zaq1@WSX");
    }

    @Test
    public void updateEmployeeTest() throws RepositoryAdapterException {
        employeeService.add(Employee.builder().firstname("Jan").lastname("Employee").password("zaq1@WSX").login("employee").build());

        Employee old = employeeService.get("employee");
        old.setFirstname("T1");
        old.setLastname("T2");
        old.setPassword("ZAQ!2wsx");

        Employee update = employeeService.update(old);

        Assert.assertEquals(update.getFirstname(), "T1");
        Assert.assertEquals(update.getLastname(), "T2");
        Assert.assertEquals(update.getPassword(), "ZAQ!2wsx");

        Assert.assertEquals(employeeRepositoryAdapter.get("employee").getFirstname(), "T1");
        Assert.assertEquals(employeeRepositoryAdapter.get("employee").getLastname(), "T2");
        Assert.assertEquals(employeeRepositoryAdapter.get("employee").getPassword(), "ZAQ!2wsx");
    }

    @Test
    public void getAllEmployeeTest() throws RepositoryAdapterException {
        employeeService.add(Employee.builder().firstname("Jan").lastname("Employee").password("zaq1@WSX").login("employee1").build());
        employeeService.add(Employee.builder().firstname("Jan").lastname("Employee").password("zaq1@WSX").login("employee2").build());
        employeeService.add(Employee.builder().firstname("Jan").lastname("Employee").password("zaq1@WSX").login("employee3").build());

        List<Employee> employeeList = employeeService.getAll();

        Assert.assertEquals(employeeList.size(), 3);
        Assert.assertEquals(employeeList.get(0).getLogin(), "employee1");
        Assert.assertEquals(employeeList.get(1).getLogin(), "employee2");
        Assert.assertEquals(employeeList.get(2).getLogin(), "employee3");

    }

    @Test
    public void getByUUIDEmployeeTest() throws RepositoryAdapterException {
        Employee a = employeeService.add(Employee.builder().firstname("Jan").lastname("Employee").password("zaq1@WSX").login("employee1").build());

        Employee get = employeeService.get(a.getId());

        Assert.assertEquals(get, a);
    }

    @Test
    public void getByLoginEmployeeTest() throws RepositoryAdapterException {
        Employee a = employeeService.add(Employee.builder().firstname("Jan").lastname("Employee").password("zaq1@WSX").login("employee1").build());

        Employee get = employeeService.get(a.getLogin());

        Assert.assertEquals(get, a);
    }
}

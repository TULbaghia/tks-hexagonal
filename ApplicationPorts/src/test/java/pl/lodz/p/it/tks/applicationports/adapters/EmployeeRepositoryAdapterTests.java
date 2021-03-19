package pl.lodz.p.it.tks.applicationports.adapters;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pl.lodz.p.it.tks.applicationports.adapters.driven.EmployeeRepositoryAdapter;
import pl.lodz.p.it.tks.applicationports.exception.RepositoryAdapterException;
import pl.lodz.p.it.tks.data.user.EmployeeEnt;
import pl.lodz.p.it.tks.domainmodel.user.Employee;
import pl.lodz.p.it.tks.repository.UserEntRepository;
import pl.lodz.p.it.tks.repository.exception.RepositoryEntException;

import java.lang.reflect.Field;

public class EmployeeRepositoryAdapterTests {
    private EmployeeRepositoryAdapter employeeRepositoryAdapter;
    private UserEntRepository userEntRepository;

    @BeforeMethod
    public void beforeTest() throws IllegalAccessException, NoSuchFieldException {
        employeeRepositoryAdapter = new EmployeeRepositoryAdapter();
        userEntRepository = new UserEntRepository();
        Field field = employeeRepositoryAdapter.getClass().getDeclaredField("userEntRepository");
        field.setAccessible(true);
        field.set(employeeRepositoryAdapter, userEntRepository);
    }

    @Test
    public void addEmployeeTest() throws RepositoryAdapterException, RepositoryEntException {
        Employee employee = Employee.builder()
                .firstname("Customer")
                .lastname("Kowalski")
                .login("Klient")
                .password("zaq1@WSX")
                .build();

        Assert.assertEquals(userEntRepository.getAll().size(), 0);
        employeeRepositoryAdapter.add(employee);
        Assert.assertEquals(userEntRepository.getAll().size(), 1);
        Assert.assertNotSame(userEntRepository.get("Klient"), employee);
    }

    @Test
    public void getEmployeeByIdTest() throws RepositoryAdapterException, RepositoryEntException {
        EmployeeEnt employee = EmployeeEnt.builder()
                .firstname("Customer")
                .lastname("Kowalski")
                .login("Klient")
                .password("zaq1@WSX")
                .build();

        Assert.assertEquals(userEntRepository.getAll().size(), 0);
        userEntRepository.add(employee);
        Assert.assertEquals(userEntRepository.getAll().size(), 1);

        Employee getEmployee = employeeRepositoryAdapter.get(employee.getId());
        Assert.assertNotSame(getEmployee, employee);
        Assert.assertEquals(getEmployee.getId(), employee.getId());
        Assert.assertEquals(getEmployee.isActive(), employee.isActive());
        Assert.assertEquals(getEmployee.getFirstname(), employee.getFirstname());
        Assert.assertEquals(getEmployee.getLastname(), employee.getLastname());
        Assert.assertEquals(getEmployee.getLogin(), employee.getLogin());
        Assert.assertEquals(getEmployee.getPassword(), employee.getPassword());
    }

    @Test
    public void getEmployeeByLoginTest() throws RepositoryAdapterException, RepositoryEntException {
        EmployeeEnt employee = EmployeeEnt.builder()
                .firstname("Customer")
                .lastname("Kowalski")
                .login("Klient")
                .password("zaq1@WSX")
                .build();

        Assert.assertEquals(userEntRepository.getAll().size(), 0);
        userEntRepository.add(employee);
        Assert.assertEquals(userEntRepository.getAll().size(), 1);

        Employee getEmployee = employeeRepositoryAdapter.get("Klient");
        Assert.assertNotSame(getEmployee, employee);
        Assert.assertEquals(getEmployee.getId(), employee.getId());
        Assert.assertEquals(getEmployee.isActive(), employee.isActive());
        Assert.assertEquals(getEmployee.getFirstname(), employee.getFirstname());
        Assert.assertEquals(getEmployee.getLastname(), employee.getLastname());
        Assert.assertEquals(getEmployee.getLogin(), employee.getLogin());
        Assert.assertEquals(getEmployee.getPassword(), employee.getPassword());
    }

    @Test
    public void getAllEmployee() throws RepositoryEntException, RepositoryAdapterException {
        int usersCount = 5;
        for (int i = 0; i < usersCount; i++) {
            EmployeeEnt employeeEnt = EmployeeEnt.builder()
                    .firstname("Customer" + i)
                    .lastname("Kowalski")
                    .login("Klient" + i)
                    .password("zaq1@WSX")
                    .build();
            userEntRepository.add(employeeEnt);
        }

        Assert.assertEquals(employeeRepositoryAdapter.getAll().size(), usersCount);
    }

    @Test
    public void updateEmployee() throws RepositoryEntException, RepositoryAdapterException {
        EmployeeEnt employeeEnt  = EmployeeEnt.builder()
                .firstname("Customer")
                .lastname("Kowalski")
                .login("Klient")
                .password("zaq1@WSX")
                .build();

        userEntRepository.add(employeeEnt);
        Assert.assertEquals(userEntRepository.getAll().size(), 1);
        Assert.assertNotEquals(employeeEnt.getId(), null);

        Employee updatedEmployee = Employee.builder()
                .id(employeeEnt.getId())
                .login(employeeEnt.getLogin())
                .firstname("Employee")
                .lastname("TestoweNazwisko")
                .password("zaq1@WSX")
                .build();

        employeeRepositoryAdapter.update(updatedEmployee);
        Assert.assertEquals(userEntRepository.getAll().size(), 1);

        EmployeeEnt getUpdatedEmployee = (EmployeeEnt) userEntRepository.get(employeeEnt.getId());
        Assert.assertEquals(getUpdatedEmployee.getId(), updatedEmployee.getId());
        Assert.assertEquals(getUpdatedEmployee.isActive(), updatedEmployee.isActive());
        Assert.assertEquals(getUpdatedEmployee.getFirstname(), updatedEmployee.getFirstname());
        Assert.assertEquals(getUpdatedEmployee.getLastname(), updatedEmployee.getLastname());
        Assert.assertEquals(getUpdatedEmployee.getLogin(), updatedEmployee.getLogin());
        Assert.assertEquals(getUpdatedEmployee.getPassword(), updatedEmployee.getPassword());

        Employee updatedEmployee2 = Employee.builder()
                .login(employeeEnt.getLogin())
                .firstname("Employee")
                .lastname("TestoweNazwisko")
                .password("zaq1@WSX")
                .build();

        Assert.assertThrows(RepositoryAdapterException.class, () -> employeeRepositoryAdapter.update(updatedEmployee2));
    }
}

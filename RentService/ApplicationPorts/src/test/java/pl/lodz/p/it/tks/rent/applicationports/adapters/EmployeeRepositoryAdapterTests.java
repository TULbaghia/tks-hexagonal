package pl.lodz.p.it.tks.rent.applicationports.adapters;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.lodz.p.it.tks.rent.applicationports.adapters.driven.EmployeeRepositoryAdapter;
import pl.lodz.p.it.tks.rent.applicationports.exception.RepositoryAdapterException;
import pl.lodz.p.it.tks.rent.data.user.EmployeeEnt;
import pl.lodz.p.it.tks.rent.domainmodel.user.Employee;
import pl.lodz.p.it.tks.rent.repository.UserEntRepository;
import pl.lodz.p.it.tks.rent.repository.exception.RepositoryEntException;

import java.lang.reflect.Field;

public class EmployeeRepositoryAdapterTests {
    private static EmployeeRepositoryAdapter employeeRepositoryAdapter;
    private static UserEntRepository userEntRepository;

    @BeforeEach
    public void beforeTest() throws IllegalAccessException, NoSuchFieldException {
        employeeRepositoryAdapter = new EmployeeRepositoryAdapter(new UserEntRepository());
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
                .build();

        Assertions.assertEquals(userEntRepository.getAll().size(), 0);
        employeeRepositoryAdapter.add(employee);
        Assertions.assertEquals(userEntRepository.getAll().size(), 1);
        Assertions.assertNotSame(userEntRepository.get("Klient"), employee);
    }

    @Test
    public void getEmployeeByIdTest() throws RepositoryAdapterException, RepositoryEntException {
        EmployeeEnt employee = EmployeeEnt.builder()
                .firstname("Customer")
                .lastname("Kowalski")
                .login("Klient")
                .build();

        Assertions.assertEquals(userEntRepository.getAll().size(), 0);
        userEntRepository.add(employee);
        Assertions.assertEquals(userEntRepository.getAll().size(), 1);

        Employee getEmployee = employeeRepositoryAdapter.get(employee.getId());
        Assertions.assertNotSame(getEmployee, employee);
        Assertions.assertEquals(getEmployee.getId(), employee.getId());
        Assertions.assertEquals(getEmployee.isActive(), employee.isActive());
        Assertions.assertEquals(getEmployee.getFirstname(), employee.getFirstname());
        Assertions.assertEquals(getEmployee.getLastname(), employee.getLastname());
        Assertions.assertEquals(getEmployee.getLogin(), employee.getLogin());
    }

    @Test
    public void getEmployeeByLoginTest() throws RepositoryAdapterException, RepositoryEntException {
        EmployeeEnt employee = EmployeeEnt.builder()
                .firstname("Customer")
                .lastname("Kowalski")
                .login("Klient")
                .build();

        Assertions.assertEquals(userEntRepository.getAll().size(), 0);
        userEntRepository.add(employee);
        Assertions.assertEquals(userEntRepository.getAll().size(), 1);

        Employee getEmployee = employeeRepositoryAdapter.get("Klient");
        Assertions.assertNotSame(getEmployee, employee);
        Assertions.assertEquals(getEmployee.getId(), employee.getId());
        Assertions.assertEquals(getEmployee.isActive(), employee.isActive());
        Assertions.assertEquals(getEmployee.getFirstname(), employee.getFirstname());
        Assertions.assertEquals(getEmployee.getLastname(), employee.getLastname());
        Assertions.assertEquals(getEmployee.getLogin(), employee.getLogin());
    }

    @Test
    public void getAllEmployee() throws RepositoryEntException, RepositoryAdapterException {
        int usersCount = 5;
        for (int i = 0; i < usersCount; i++) {
            EmployeeEnt employeeEnt = EmployeeEnt.builder()
                    .firstname("Customer" + i)
                    .lastname("Kowalski")
                    .login("Klient" + i)
                    .build();
            userEntRepository.add(employeeEnt);
        }

        Assertions.assertEquals(employeeRepositoryAdapter.getAll().size(), usersCount);
    }

    @Test
    public void updateEmployee() throws RepositoryEntException, RepositoryAdapterException {
        EmployeeEnt employeeEnt  = EmployeeEnt.builder()
                .firstname("Customer")
                .lastname("Kowalski")
                .login("Klient")
                .build();

        userEntRepository.add(employeeEnt);
        Assertions.assertEquals(userEntRepository.getAll().size(), 1);
        Assertions.assertNotEquals(employeeEnt.getId(), null);

        Employee updatedEmployee = Employee.builder()
                .id(employeeEnt.getId())
                .login(employeeEnt.getLogin())
                .firstname("Employee")
                .lastname("TestoweNazwisko")
                .build();

        employeeRepositoryAdapter.update(updatedEmployee);
        Assertions.assertEquals(userEntRepository.getAll().size(), 1);

        EmployeeEnt getUpdatedEmployee = (EmployeeEnt) userEntRepository.get(employeeEnt.getId());
        Assertions.assertEquals(getUpdatedEmployee.getId(), updatedEmployee.getId());
        Assertions.assertEquals(getUpdatedEmployee.isActive(), updatedEmployee.isActive());
        Assertions.assertEquals(getUpdatedEmployee.getFirstname(), updatedEmployee.getFirstname());
        Assertions.assertEquals(getUpdatedEmployee.getLastname(), updatedEmployee.getLastname());
        Assertions.assertEquals(getUpdatedEmployee.getLogin(), updatedEmployee.getLogin());

        Employee updatedEmployee2 = Employee.builder()
                .login(employeeEnt.getLogin())
                .firstname("Employee")
                .lastname("TestoweNazwisko")
                .build();

        Assertions.assertThrows(RepositoryAdapterException.class, () -> employeeRepositoryAdapter.update(updatedEmployee2));
    }
}

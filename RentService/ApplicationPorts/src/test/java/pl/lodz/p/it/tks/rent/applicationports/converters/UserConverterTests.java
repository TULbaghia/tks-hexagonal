package pl.lodz.p.it.tks.rent.applicationports.converters;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.lodz.p.it.tks.rent.data.user.AdminEnt;
import pl.lodz.p.it.tks.rent.data.user.CustomerEnt;
import pl.lodz.p.it.tks.rent.data.user.EmployeeEnt;
import pl.lodz.p.it.tks.rent.domainmodel.user.Admin;
import pl.lodz.p.it.tks.rent.domainmodel.user.Customer;
import pl.lodz.p.it.tks.rent.domainmodel.user.Employee;

public class UserConverterTests {

    @Test
    public void customerDomainToEntConverterTest() {
        Customer customer = Customer.builder()
                .firstname("Customer")
                .lastname("Kowalski")
                .login("Klient")
                .build();

        CustomerEnt customerEnt = UserConverter.convertDomainToEnt(customer);

        Assertions.assertEquals(customerEnt.getId(), customer.getId());
        Assertions.assertEquals(customerEnt.isActive(), customer.isActive());
        Assertions.assertEquals(customerEnt.getFirstname(), customer.getFirstname());
        Assertions.assertEquals(customerEnt.getLastname(), customer.getLastname());
        Assertions.assertEquals(customerEnt.getLogin(), customer.getLogin());
        Assertions.assertEquals(customerEnt.getRentsNumber(), customer.getRentsNumber());
    }

    @Test
    public void employeeDomainToEntConverterTest() {
        Employee employee = Employee.builder()
                .firstname("Customer")
                .lastname("Kowalski")
                .login("Klient")
                .build();

        EmployeeEnt employeeEnt = UserConverter.convertDomainToEnt(employee);

        Assertions.assertEquals(employeeEnt.getId(), employee.getId());
        Assertions.assertEquals(employeeEnt.isActive(), employee.isActive());
        Assertions.assertEquals(employeeEnt.getFirstname(), employee.getFirstname());
        Assertions.assertEquals(employeeEnt.getLastname(), employee.getLastname());
        Assertions.assertEquals(employeeEnt.getLogin(), employee.getLogin());
    }

    @Test
    public void adminDomainToEntConverterTest() {
        Admin admin = Admin.builder()
                .firstname("Customer")
                .lastname("Kowalski")
                .login("Klient")
                .build();

        AdminEnt adminEnt = UserConverter.convertDomainToEnt(admin);

        Assertions.assertEquals(adminEnt.getId(), admin.getId());
        Assertions.assertEquals(adminEnt.isActive(), admin.isActive());
        Assertions.assertEquals(adminEnt.getFirstname(), admin.getFirstname());
        Assertions.assertEquals(adminEnt.getLastname(), admin.getLastname());
        Assertions.assertEquals(adminEnt.getLogin(), admin.getLogin());
    }
}

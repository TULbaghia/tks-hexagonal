package pl.lodz.p.it.tks.rent.applicationports.converters;

import org.testng.Assert;
import org.testng.annotations.Test;
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

        Assert.assertEquals(customerEnt.getId(), customer.getId());
        Assert.assertEquals(customerEnt.isActive(), customer.isActive());
        Assert.assertEquals(customerEnt.getFirstname(), customer.getFirstname());
        Assert.assertEquals(customerEnt.getLastname(), customer.getLastname());
        Assert.assertEquals(customerEnt.getLogin(), customer.getLogin());
        Assert.assertEquals(customerEnt.getRentsNumber(), customer.getRentsNumber());
    }

    @Test
    public void employeeDomainToEntConverterTest() {
        Employee employee = Employee.builder()
                .firstname("Customer")
                .lastname("Kowalski")
                .login("Klient")
                .build();

        EmployeeEnt employeeEnt = UserConverter.convertDomainToEnt(employee);

        Assert.assertEquals(employeeEnt.getId(), employee.getId());
        Assert.assertEquals(employeeEnt.isActive(), employee.isActive());
        Assert.assertEquals(employeeEnt.getFirstname(), employee.getFirstname());
        Assert.assertEquals(employeeEnt.getLastname(), employee.getLastname());
        Assert.assertEquals(employeeEnt.getLogin(), employee.getLogin());
    }

    @Test
    public void adminDomainToEntConverterTest() {
        Admin admin = Admin.builder()
                .firstname("Customer")
                .lastname("Kowalski")
                .login("Klient")
                .build();

        AdminEnt adminEnt = UserConverter.convertDomainToEnt(admin);

        Assert.assertEquals(adminEnt.getId(), admin.getId());
        Assert.assertEquals(adminEnt.isActive(), admin.isActive());
        Assert.assertEquals(adminEnt.getFirstname(), admin.getFirstname());
        Assert.assertEquals(adminEnt.getLastname(), admin.getLastname());
        Assert.assertEquals(adminEnt.getLogin(), admin.getLogin());
    }
}

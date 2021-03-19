package pl.lodz.p.it.tks.applicationports.converters;

import org.testng.Assert;
import org.testng.annotations.Test;
import pl.lodz.p.it.tks.applicationports.converters.UserConverter;
import pl.lodz.p.it.tks.data.user.AdminEnt;
import pl.lodz.p.it.tks.data.user.CustomerEnt;
import pl.lodz.p.it.tks.data.user.EmployeeEnt;
import pl.lodz.p.it.tks.domainmodel.user.Admin;
import pl.lodz.p.it.tks.domainmodel.user.Customer;
import pl.lodz.p.it.tks.domainmodel.user.Employee;

public class UserConverterTests {

    @Test
    public void customerDomainToEntConverterTest() {
        Customer customer = Customer.builder()
                .firstname("Customer")
                .lastname("Kowalski")
                .login("Klient")
                .password("zaq1@WSX")
                .build();

        CustomerEnt customerEnt = UserConverter.convertDomainToEnt(customer);

        Assert.assertEquals(customerEnt.getId(), customer.getId());
        Assert.assertEquals(customerEnt.isActive(), customer.isActive());
        Assert.assertEquals(customerEnt.getFirstname(), customer.getFirstname());
        Assert.assertEquals(customerEnt.getLastname(), customer.getLastname());
        Assert.assertEquals(customerEnt.getLogin(), customer.getLogin());
        Assert.assertEquals(customerEnt.getPassword(), customer.getPassword());
        Assert.assertEquals(customerEnt.getRentsNumber(), customer.getRentsNumber());
    }

    @Test
    public void employeeDomainToEntConverterTest() {
        Employee employee = Employee.builder()
                .firstname("Customer")
                .lastname("Kowalski")
                .login("Klient")
                .password("zaq1@WSX")
                .build();

        EmployeeEnt employeeEnt = UserConverter.convertDomainToEnt(employee);

        Assert.assertEquals(employeeEnt.getId(), employee.getId());
        Assert.assertEquals(employeeEnt.isActive(), employee.isActive());
        Assert.assertEquals(employeeEnt.getFirstname(), employee.getFirstname());
        Assert.assertEquals(employeeEnt.getLastname(), employee.getLastname());
        Assert.assertEquals(employeeEnt.getLogin(), employee.getLogin());
        Assert.assertEquals(employeeEnt.getPassword(), employee.getPassword());
    }

    @Test
    public void adminDomainToEntConverterTest() {
        Admin admin = Admin.builder()
                .firstname("Customer")
                .lastname("Kowalski")
                .login("Klient")
                .password("zaq1@WSX")
                .build();

        AdminEnt adminEnt = UserConverter.convertDomainToEnt(admin);

        Assert.assertEquals(adminEnt.getId(), admin.getId());
        Assert.assertEquals(adminEnt.isActive(), admin.isActive());
        Assert.assertEquals(adminEnt.getFirstname(), admin.getFirstname());
        Assert.assertEquals(adminEnt.getLastname(), admin.getLastname());
        Assert.assertEquals(adminEnt.getLogin(), admin.getLogin());
        Assert.assertEquals(adminEnt.getPassword(), admin.getPassword());
    }
}

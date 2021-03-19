package pl.lodz.p.it.tks.applicationports.converters;

import pl.lodz.p.it.tks.data.user.AdminEnt;
import pl.lodz.p.it.tks.data.user.CustomerEnt;
import pl.lodz.p.it.tks.data.user.EmployeeEnt;
import pl.lodz.p.it.tks.domainmodel.user.Admin;
import pl.lodz.p.it.tks.domainmodel.user.Customer;
import pl.lodz.p.it.tks.domainmodel.user.Employee;

public class UserConverter {
    public static CustomerEnt convertDomainToEnt(Customer customer) {
        return CustomerEnt.builder()
                .id(customer.getId())
                .firstname(customer.getFirstname())
                .lastname(customer.getLastname())
                .login(customer.getLogin())
                .password(customer.getPassword())
                .isActive(customer.isActive())
                .rentsNumber(customer.getRentsNumber())
                .build();
    }

    public static Customer convertEntToDomain(CustomerEnt customerEnt) {
        return Customer.builder()
                .id(customerEnt.getId())
                .firstname(customerEnt.getFirstname())
                .lastname(customerEnt.getLastname())
                .login(customerEnt.getLogin())
                .password(customerEnt.getPassword())
                .isActive(customerEnt.isActive())
                .rentsNumber(customerEnt.getRentsNumber())
                .build();
    }

    public static EmployeeEnt convertDomainToEnt(Employee employee) {
        return EmployeeEnt.builder()
                .id(employee.getId())
                .firstname(employee.getFirstname())
                .lastname(employee.getLastname())
                .login(employee.getLogin())
                .password(employee.getPassword())
                .isActive(employee.isActive())
                .build();
    }

    public static Employee convertEntToDomain(EmployeeEnt employeeEnt) {
        return Employee.builder()
                .id(employeeEnt.getId())
                .firstname(employeeEnt.getFirstname())
                .lastname(employeeEnt.getLastname())
                .login(employeeEnt.getLogin())
                .password(employeeEnt.getPassword())
                .isActive(employeeEnt.isActive())
                .build();
    }

    public static AdminEnt convertDomainToEnt(Admin admin) {
        return AdminEnt.builder()
                .id(admin.getId())
                .firstname(admin.getFirstname())
                .lastname(admin.getLastname())
                .login(admin.getLogin())
                .password(admin.getPassword())
                .isActive(admin.isActive())
                .build();
    }

    public static Admin convertEntToDomain(AdminEnt adminEnt) {
        return Admin.builder()
                .id(adminEnt.getId())
                .firstname(adminEnt.getFirstname())
                .lastname(adminEnt.getLastname())
                .login(adminEnt.getLogin())
                .password(adminEnt.getPassword())
                .isActive(adminEnt.isActive())
                .build();
    }
}

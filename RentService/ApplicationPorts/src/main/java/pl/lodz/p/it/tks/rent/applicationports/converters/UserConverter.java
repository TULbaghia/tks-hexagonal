package pl.lodz.p.it.tks.rent.applicationports.converters;

import pl.lodz.p.it.tks.rent.data.user.AdminEnt;
import pl.lodz.p.it.tks.rent.data.user.CustomerEnt;
import pl.lodz.p.it.tks.rent.data.user.EmployeeEnt;
import pl.lodz.p.it.tks.rent.domainmodel.user.Admin;
import pl.lodz.p.it.tks.rent.domainmodel.user.Customer;
import pl.lodz.p.it.tks.rent.domainmodel.user.Employee;

public class UserConverter {
    public static CustomerEnt convertDomainToEnt(Customer customer) {
        return CustomerEnt.builder()
                .id(customer.getId())
                .firstname(customer.getFirstname())
                .lastname(customer.getLastname())
                .login(customer.getLogin())
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
                .isActive(employee.isActive())
                .build();
    }

    public static Employee convertEntToDomain(EmployeeEnt employeeEnt) {
        return Employee.builder()
                .id(employeeEnt.getId())
                .firstname(employeeEnt.getFirstname())
                .lastname(employeeEnt.getLastname())
                .login(employeeEnt.getLogin())
                .isActive(employeeEnt.isActive())
                .build();
    }

    public static AdminEnt convertDomainToEnt(Admin admin) {
        return AdminEnt.builder()
                .id(admin.getId())
                .firstname(admin.getFirstname())
                .lastname(admin.getLastname())
                .login(admin.getLogin())
                .isActive(admin.isActive())
                .build();
    }

    public static Admin convertEntToDomain(AdminEnt adminEnt) {
        return Admin.builder()
                .id(adminEnt.getId())
                .firstname(adminEnt.getFirstname())
                .lastname(adminEnt.getLastname())
                .login(adminEnt.getLogin())
                .isActive(adminEnt.isActive())
                .build();
    }
}

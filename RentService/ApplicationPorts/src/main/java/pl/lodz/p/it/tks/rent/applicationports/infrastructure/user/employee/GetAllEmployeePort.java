package pl.lodz.p.it.tks.rent.applicationports.infrastructure.user.employee;

import pl.lodz.p.it.tks.rent.domainmodel.user.Employee;

import java.util.List;

public interface GetAllEmployeePort {
    List<Employee> getAll();
}

package pl.lodz.p.it.tks.rent.applicationports.infrastructure.user.employee;

import pl.lodz.p.it.tks.rent.applicationports.exception.RepositoryAdapterException;
import pl.lodz.p.it.tks.rent.domainmodel.user.Employee;

public interface AddEmployeePort {
    Employee add(Employee employee) throws RepositoryAdapterException;
}

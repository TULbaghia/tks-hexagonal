package pl.lodz.p.it.tks.applicationports.infrastructure.user.employee;

import pl.lodz.p.it.tks.applicationports.exception.RepositoryAdapterException;
import pl.lodz.p.it.tks.domainmodel.user.Employee;

public interface AddEmployeePort {
    Employee add(Employee employee) throws RepositoryAdapterException;
}

package pl.lodz.p.it.tks.applicationports.infrastructure.user.employee;

import pl.lodz.p.it.tks.applicationports.exception.RepositoryAdapterException;
import pl.lodz.p.it.tks.domainmodel.user.Employee;

public interface UpdateEmployeePort {
    Employee update(Employee employee) throws RepositoryAdapterException;
}

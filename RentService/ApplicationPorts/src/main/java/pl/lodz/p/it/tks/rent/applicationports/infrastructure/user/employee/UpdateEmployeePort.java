package pl.lodz.p.it.tks.rent.applicationports.infrastructure.user.employee;

import pl.lodz.p.it.tks.rent.applicationports.exception.RepositoryAdapterException;
import pl.lodz.p.it.tks.rent.domainmodel.user.Employee;

public interface UpdateEmployeePort {
    Employee update(Employee employee) throws RepositoryAdapterException;
}

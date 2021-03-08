package pl.lodz.p.it.tks.applicationports.infrastructure.user;

import pl.lodz.p.it.tks.applicationports.exception.RepositoryAdapterException;
import pl.lodz.p.it.tks.domainmodel.user.Employee;

public interface AddEmployeePort {
    void add(Employee employee) throws RepositoryAdapterException;
}

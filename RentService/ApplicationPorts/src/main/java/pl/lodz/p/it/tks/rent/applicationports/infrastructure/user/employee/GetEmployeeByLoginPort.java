package pl.lodz.p.it.tks.rent.applicationports.infrastructure.user.employee;

import pl.lodz.p.it.tks.rent.applicationports.exception.RepositoryAdapterException;
import pl.lodz.p.it.tks.rent.domainmodel.user.Employee;

public interface GetEmployeeByLoginPort {
    Employee get(String login) throws RepositoryAdapterException;
}

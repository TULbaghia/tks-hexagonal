package pl.lodz.p.it.tks.applicationports.infrastructure.user.employee;

import pl.lodz.p.it.tks.applicationports.exception.RepositoryAdapterException;
import pl.lodz.p.it.tks.domainmodel.user.Admin;
import pl.lodz.p.it.tks.domainmodel.user.Employee;

import java.util.UUID;

public interface GetEmployeeByIdPort {
    Employee get(UUID uuid) throws RepositoryAdapterException;
}

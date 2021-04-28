package pl.lodz.p.it.tks.rent.applicationports.infrastructure.user.employee;

import pl.lodz.p.it.tks.rent.applicationports.exception.RepositoryAdapterException;
import pl.lodz.p.it.tks.rent.domainmodel.user.Employee;

import java.util.UUID;

public interface GetEmployeeByIdPort {
    Employee get(UUID uuid) throws RepositoryAdapterException;
}

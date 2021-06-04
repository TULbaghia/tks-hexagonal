package pl.lodz.p.it.tks.rent.applicationports.infrastructure.user.employee;

import pl.lodz.p.it.tks.rent.applicationports.exception.RepositoryAdapterException;
import pl.lodz.p.it.tks.rent.domainmodel.user.Admin;

import java.util.UUID;

public interface DeleteEmployeePort {
    void delete(UUID uuid) throws RepositoryAdapterException;
}

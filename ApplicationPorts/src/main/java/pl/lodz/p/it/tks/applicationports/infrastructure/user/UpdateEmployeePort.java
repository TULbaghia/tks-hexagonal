package pl.lodz.p.it.tks.applicationports.infrastructure.user;

import lombok.NonNull;
import pl.lodz.p.it.tks.applicationports.exception.RepositoryAdapterException;
import pl.lodz.p.it.tks.domainmodel.user.Employee;

public interface UpdateEmployeePort {
    void update(Employee employee) throws RepositoryAdapterException;
}

package pl.lodz.p.it.tks.rent.applicationports.ui;

import pl.lodz.p.it.tks.rent.applicationports.exception.RepositoryAdapterException;
import pl.lodz.p.it.tks.rent.domainmodel.user.Employee;

import java.util.List;
import java.util.UUID;

public interface EmployeeUseCase {

    Employee add(Employee employee) throws RepositoryAdapterException;

    Employee update(Employee employee) throws RepositoryAdapterException;

    List<Employee> getAll();

    Employee get(UUID uuid) throws RepositoryAdapterException;

    Employee get(String login) throws RepositoryAdapterException;
}

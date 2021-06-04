package pl.lodz.p.it.tks.rent.applicationservices;

import pl.lodz.p.it.tks.rent.applicationports.exception.RepositoryAdapterException;
import pl.lodz.p.it.tks.rent.applicationports.infrastructure.user.employee.*;
import pl.lodz.p.it.tks.rent.applicationports.ui.EmployeeUseCase;
import pl.lodz.p.it.tks.rent.domainmodel.user.Employee;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class EmployeeService implements EmployeeUseCase {
    private final AddEmployeePort addEmployeePort;
    private final UpdateEmployeePort updateEmployeePort;
    private final GetAllEmployeePort getAllEmployeePort;
    private final GetEmployeeByIdPort getEmployeeByIdPort;
    private final GetEmployeeByLoginPort getEmployeeByLoginPort;
    private final DeleteEmployeePort deleteEmployeePort;

    @Inject
    public EmployeeService(DeleteEmployeePort deleteEmployeePort, AddEmployeePort addEmployeePort, UpdateEmployeePort updateEmployeePort, GetAllEmployeePort getAllEmployeePort, GetEmployeeByIdPort getEmployeeByIdPort, GetEmployeeByLoginPort getEmployeeByLoginPort) {
        this.addEmployeePort = addEmployeePort;
        this.updateEmployeePort = updateEmployeePort;
        this.getAllEmployeePort = getAllEmployeePort;
        this.getEmployeeByIdPort = getEmployeeByIdPort;
        this.getEmployeeByLoginPort = getEmployeeByLoginPort;
        this.deleteEmployeePort = deleteEmployeePort;
    }

    @Override
    public Employee add(Employee employee) throws RepositoryAdapterException {
        if (getAll().stream().anyMatch(x -> x.getLogin().equals(employee.getLogin()))) {
            throw new RepositoryAdapterException("User with this login already exists.");
        }
        return addEmployeePort.add(employee);
    }

    @Override
    public Employee update(Employee employee) throws RepositoryAdapterException {
        if (getAll().stream().anyMatch(x -> x.getLogin().equals(employee.getLogin()) && !x.getId().equals(employee.getId()))) {
            throw new RepositoryAdapterException("User with this login already exists.");
        }
        return updateEmployeePort.update(employee);
    }

    @Override
    public List<Employee> getAll() {
        return getAllEmployeePort.getAll();
    }

    @Override
    public Employee get(UUID uuid) throws RepositoryAdapterException {
        return getEmployeeByIdPort.get(uuid);
    }

    @Override
    public Employee get(String login) throws RepositoryAdapterException {
        return getEmployeeByLoginPort.get(login);
    }

    @Override
    public void delete(UUID uuid) throws RepositoryAdapterException {
        deleteEmployeePort.delete(uuid);
    }
}

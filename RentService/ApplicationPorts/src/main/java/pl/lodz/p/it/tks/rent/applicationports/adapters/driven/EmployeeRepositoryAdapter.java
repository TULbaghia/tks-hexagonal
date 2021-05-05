package pl.lodz.p.it.tks.rent.applicationports.adapters.driven;

import pl.lodz.p.it.tks.rent.applicationports.converters.UserConverter;
import pl.lodz.p.it.tks.rent.applicationports.exception.RepositoryAdapterException;
import pl.lodz.p.it.tks.rent.applicationports.infrastructure.user.employee.*;
import pl.lodz.p.it.tks.rent.data.user.EmployeeEnt;
import pl.lodz.p.it.tks.rent.domainmodel.user.Employee;
import pl.lodz.p.it.tks.rent.applicationports.infrastructure.user.employee.*;
import pl.lodz.p.it.tks.rent.applicationports.infrastructure.user.employee.*;
import pl.lodz.p.it.tks.rent.repository.UserEntRepository;
import pl.lodz.p.it.tks.rent.repository.exception.RepositoryEntException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@ApplicationScoped
public class EmployeeRepositoryAdapter implements AddEmployeePort, UpdateEmployeePort, GetAllEmployeePort,
        GetEmployeeByLoginPort, GetEmployeeByIdPort {

    private final UserEntRepository userEntRepository;

    @Inject
    public EmployeeRepositoryAdapter(UserEntRepository userEntRepository) {
        this.userEntRepository = userEntRepository;
    }

    @Override
    public Employee add(Employee employee) throws RepositoryAdapterException {
        EmployeeEnt employeeEnt = UserConverter.convertDomainToEnt(employee);
        try {
            return UserConverter.convertEntToDomain((EmployeeEnt) userEntRepository.add(employeeEnt));
        } catch (RepositoryEntException e) {
            throw new RepositoryAdapterException(e);
        }
    }

    @Override
    public Employee get(UUID uuid) throws RepositoryAdapterException {
        try {
            return UserConverter.convertEntToDomain((EmployeeEnt) userEntRepository.get(uuid));
        } catch (RepositoryEntException e) {
            throw new RepositoryAdapterException(e);
        }
    }

    @Override
    public Employee get(String login) throws RepositoryAdapterException {
        try {
            return UserConverter.convertEntToDomain((EmployeeEnt) userEntRepository.get(login));
        } catch (RepositoryEntException e) {
            throw new RepositoryAdapterException(e);
        }
    }

    @Override
    public List<Employee> getAll() {
        return userEntRepository.getAll().stream()
                .filter(x -> x instanceof EmployeeEnt)
                .map(x -> UserConverter.convertEntToDomain((EmployeeEnt) x))
                .collect(Collectors.toList());
    }

    @Override
    public Employee update(Employee employee) throws RepositoryAdapterException {
        EmployeeEnt employeeEnt = UserConverter.convertDomainToEnt(employee);
        try {
            return UserConverter.convertEntToDomain((EmployeeEnt) userEntRepository.update(employeeEnt));
        } catch (RepositoryEntException e) {
            throw new RepositoryAdapterException(e);
        }
    }
}

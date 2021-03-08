package pl.lodz.p.it.tks.applicationports.adapters.driven;

import lombok.NonNull;
import pl.lodz.p.it.tks.applicationports.converters.UserConverter;
import pl.lodz.p.it.tks.applicationports.exception.RepositoryAdapterException;
import pl.lodz.p.it.tks.applicationports.infrastructure.user.*;
import pl.lodz.p.it.tks.data.user.AdminEnt;
import pl.lodz.p.it.tks.data.user.CustomerEnt;
import pl.lodz.p.it.tks.data.user.EmployeeEnt;
import pl.lodz.p.it.tks.data.user.UserEnt;
import pl.lodz.p.it.tks.domainmodel.user.Admin;
import pl.lodz.p.it.tks.domainmodel.user.Customer;
import pl.lodz.p.it.tks.domainmodel.user.Employee;
import pl.lodz.p.it.tks.domainmodel.user.User;
import pl.lodz.p.it.tks.repository.UserEntRepository;
import pl.lodz.p.it.tks.repository.exception.RepositoryEntException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@ApplicationScoped
public class UserRepositoryAdapter implements AddAdminPort, AddEmployeePort, AddCustomerPort,
        GetUserByIdPort, GetUserByLoginPort, GetAllUserPort,
        UpdateAdminPort, UpdateEmployeePort, UpdateCustomerPort {

    @Inject
    private UserEntRepository userEntRepository;

    private void addUser(UserEnt userEnt) throws RepositoryAdapterException {
        try {
            userEntRepository.add(userEnt);
        } catch (RepositoryEntException e) {
            throw new RepositoryAdapterException(e);
        }
    }

    private void updateUser(UserEnt userEnt) throws RepositoryAdapterException {
        try {
            userEntRepository.update(userEnt);
        } catch (RepositoryEntException | IllegalAccessException | InvocationTargetException e) {
            throw new RepositoryAdapterException(e);
        }
    }

    @Override
    public void add(Admin admin) throws RepositoryAdapterException {
        AdminEnt adminEnt = UserConverter.convertDomainToEnt(admin);
        addUser(adminEnt);
    }

    @Override
    public void add(Customer customer) throws RepositoryAdapterException {
        CustomerEnt customerEnt = UserConverter.convertDomainToEnt(customer);
        addUser(customerEnt);
    }

    @Override
    public void add(Employee employee) throws RepositoryAdapterException {
        EmployeeEnt employeeEnt = UserConverter.convertDomainToEnt(employee);
        addUser(employeeEnt);
    }

    @Override
    public List<User> getAll() {
        return userEntRepository.getAll().stream()
                .map(x -> {
                    if (x instanceof CustomerEnt) {
                        return UserConverter.convertEntToDomain((CustomerEnt) x);
                    } else if (x instanceof EmployeeEnt) {
                        return UserConverter.convertEntToDomain((EmployeeEnt) x);
                    } else {
                        return UserConverter.convertEntToDomain((AdminEnt) x);
                    }
                }).collect(Collectors.toList());
    }

    @Override
    public User get(UUID uuid) {
        UserEnt userEnt = userEntRepository.get(uuid);
        int i = 5;
        if (userEnt instanceof CustomerEnt) {
            return UserConverter.convertEntToDomain((CustomerEnt) userEnt);
        } else if (userEnt instanceof EmployeeEnt) {
            return UserConverter.convertEntToDomain((EmployeeEnt) userEnt);
        } else if (userEnt instanceof AdminEnt) {
            return UserConverter.convertEntToDomain((AdminEnt) userEnt);
        }
        return null;
    }

    @Override
    public User get(String login) {
        UserEnt userEnt = userEntRepository.get(login);
        if (userEnt instanceof CustomerEnt) {
            return UserConverter.convertEntToDomain((CustomerEnt) userEnt);
        } else if (userEnt instanceof EmployeeEnt) {
            return UserConverter.convertEntToDomain((EmployeeEnt) userEnt);
        } else if (userEnt instanceof AdminEnt){
            return UserConverter.convertEntToDomain((AdminEnt) userEnt);
        }
        return null;
    }

    @Override
    public void update(Admin admin) throws RepositoryAdapterException {
        AdminEnt adminEnt = UserConverter.convertDomainToEnt(admin);
        updateUser(adminEnt);
    }

    @Override
    public void update(Customer customer) throws RepositoryAdapterException {
        CustomerEnt customerEnt = UserConverter.convertDomainToEnt(customer);
        updateUser(customerEnt);
    }

    @Override
    public void update(Employee employee) throws RepositoryAdapterException {
        EmployeeEnt employeeEnt = UserConverter.convertDomainToEnt(employee);
        updateUser(employeeEnt);
    }
}

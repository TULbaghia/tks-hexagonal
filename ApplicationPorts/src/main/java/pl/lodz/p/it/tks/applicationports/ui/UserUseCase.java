package pl.lodz.p.it.tks.applicationports.ui;

import pl.lodz.p.it.tks.applicationports.exception.RepositoryAdapterException;
import pl.lodz.p.it.tks.domainmodel.user.Admin;
import pl.lodz.p.it.tks.domainmodel.user.Customer;
import pl.lodz.p.it.tks.domainmodel.user.Employee;
import pl.lodz.p.it.tks.domainmodel.user.User;

import java.util.List;
import java.util.UUID;

public interface UserUseCase {
    void add(Admin admin) throws RepositoryAdapterException;

    void add(Employee employee) throws RepositoryAdapterException;

    void add(Customer customer) throws RepositoryAdapterException;

    void update(Admin admin) throws RepositoryAdapterException;

    void update(Employee employee) throws RepositoryAdapterException;

    void update(Customer customer) throws RepositoryAdapterException;

    List<User> getAll();

    User get(UUID uuid);

    User get(String login);
}

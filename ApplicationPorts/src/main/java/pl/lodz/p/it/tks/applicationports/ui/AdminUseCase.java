package pl.lodz.p.it.tks.applicationports.ui;

import pl.lodz.p.it.tks.applicationports.exception.RepositoryAdapterException;
import pl.lodz.p.it.tks.domainmodel.user.Admin;
import pl.lodz.p.it.tks.domainmodel.user.Customer;
import pl.lodz.p.it.tks.domainmodel.user.Employee;

import java.util.List;
import java.util.UUID;

public interface AdminUseCase {
    Admin add(Admin admin) throws RepositoryAdapterException;

    Admin update(Admin admin) throws RepositoryAdapterException;

    List<Admin> getAll();

    Admin get(UUID uuid) throws RepositoryAdapterException;

    Admin get(String login) throws RepositoryAdapterException;
}

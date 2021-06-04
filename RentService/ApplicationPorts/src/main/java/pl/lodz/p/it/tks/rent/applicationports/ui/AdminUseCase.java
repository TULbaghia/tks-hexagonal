package pl.lodz.p.it.tks.rent.applicationports.ui;

import pl.lodz.p.it.tks.rent.applicationports.exception.RepositoryAdapterException;
import pl.lodz.p.it.tks.rent.domainmodel.user.Admin;

import java.util.List;
import java.util.UUID;

public interface AdminUseCase {
    Admin add(Admin admin) throws RepositoryAdapterException;

    Admin update(Admin admin) throws RepositoryAdapterException;

    List<Admin> getAll();

    Admin get(UUID uuid) throws RepositoryAdapterException;

    Admin get(String login) throws RepositoryAdapterException;

    void delete(UUID uuid) throws RepositoryAdapterException;
}

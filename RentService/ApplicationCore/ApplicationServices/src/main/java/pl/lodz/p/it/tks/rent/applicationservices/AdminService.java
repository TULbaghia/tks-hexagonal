package pl.lodz.p.it.tks.rent.applicationservices;

import pl.lodz.p.it.tks.rent.applicationports.exception.RepositoryAdapterException;
import pl.lodz.p.it.tks.rent.applicationports.infrastructure.user.admin.*;
import pl.lodz.p.it.tks.rent.applicationports.ui.AdminUseCase;
import pl.lodz.p.it.tks.rent.domainmodel.user.Admin;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class AdminService implements AdminUseCase {
    private final AddAdminPort addAdminPort;
    private final UpdateAdminPort updateAdminPort;
    private final GetAllAdminPort getAllAdminPort;
    private final GetAdminByIdPort getAdminByIdPort;
    private final GetAdminByLoginPort getAdminByLoginPort;
    private final DeleteAdminPort deleteAdminPort;

    @Inject
    public AdminService(DeleteAdminPort deleteAdminPort, AddAdminPort addAdminPort, UpdateAdminPort updateAdminPort, GetAllAdminPort getAllAdminPort, GetAdminByIdPort getAdminByIdPort, GetAdminByLoginPort getAdminByLoginPort) {
        this.addAdminPort = addAdminPort;
        this.updateAdminPort = updateAdminPort;
        this.getAllAdminPort = getAllAdminPort;
        this.getAdminByIdPort = getAdminByIdPort;
        this.getAdminByLoginPort = getAdminByLoginPort;
        this.deleteAdminPort = deleteAdminPort;
    }

    @Override
    public Admin add(Admin admin) throws RepositoryAdapterException {
        if (getAll().stream().anyMatch(x -> x.getLogin().equals(admin.getLogin()))) {
            throw new RepositoryAdapterException("User with this login already exists.");
        }
        return addAdminPort.add(admin);
    }

    @Override
    public Admin update(Admin admin) throws RepositoryAdapterException {
        if (getAll().stream().anyMatch(x -> x.getLogin().equals(admin.getLogin()) && !x.getId().equals(admin.getId()))) {
            throw new RepositoryAdapterException("User with this login already exists.");
        }
        return updateAdminPort.update(admin);
    }

    @Override
    public List<Admin> getAll() {
        return getAllAdminPort.getAll();
    }

    @Override
    public Admin get(UUID uuid) throws RepositoryAdapterException {
        return getAdminByIdPort.get(uuid);
    }

    @Override
    public Admin get(String login) throws RepositoryAdapterException {
        return getAdminByLoginPort.get(login);
    }

    @Override
    public void delete(UUID uuid) throws RepositoryAdapterException {
        deleteAdminPort.delete(uuid);
    }
}

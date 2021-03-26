package pl.lodz.p.it.tks.applicationservices;

import pl.lodz.p.it.tks.applicationports.exception.RepositoryAdapterException;
import pl.lodz.p.it.tks.applicationports.infrastructure.user.admin.*;
import pl.lodz.p.it.tks.applicationports.ui.AdminUseCase;
import pl.lodz.p.it.tks.applicationservices.exception.CoreServiceException;
import pl.lodz.p.it.tks.domainmodel.user.Admin;

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

    @Inject
    public AdminService(AddAdminPort addAdminPort, UpdateAdminPort updateAdminPort, GetAllAdminPort getAllAdminPort, GetAdminByIdPort getAdminByIdPort, GetAdminByLoginPort getAdminByLoginPort) {
        this.addAdminPort = addAdminPort;
        this.updateAdminPort = updateAdminPort;
        this.getAllAdminPort = getAllAdminPort;
        this.getAdminByIdPort = getAdminByIdPort;
        this.getAdminByLoginPort = getAdminByLoginPort;
    }

    @Override
    public Admin add(Admin admin) throws RepositoryAdapterException {
        checkPassword(admin.getPassword());
        if (getAll().stream().anyMatch(x -> x.getLogin().equals(admin.getLogin()))) {
            throw new RepositoryAdapterException("User with this login already exists.");
        }
        return addAdminPort.add(admin);
    }

    @Override
    public Admin update(Admin admin) throws RepositoryAdapterException {
        checkPassword(admin.getPassword());
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

    private void checkPassword(String password) throws RepositoryAdapterException {
        if (!password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$")) {
            throw new RepositoryAdapterException("Password is not secure.");
        }
    }
}

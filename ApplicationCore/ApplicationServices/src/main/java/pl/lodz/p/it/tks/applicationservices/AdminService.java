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
    @Inject
    private AddAdminPort addAdminPort;
    @Inject
    private UpdateAdminPort updateAdminPort;
    @Inject
    private GetAllAdminPort getAllAdminPort;
    @Inject
    private GetAdminByIdPort getAdminByIdPort;
    @Inject
    private GetAdminByLoginPort getAdminByLoginPort;

    @Override
    public Admin add(Admin admin) throws RepositoryAdapterException {
        checkPassword(admin.getPassword());
        return addAdminPort.add(admin);
    }

    @Override
    public Admin update(Admin admin) throws RepositoryAdapterException {
        checkPassword(admin.getPassword());
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

    private void checkPassword(String password) throws CoreServiceException {
        if (!password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$")) {
            throw new CoreServiceException("Password is not secure.");
        }
    }
}

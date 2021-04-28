package pl.lodz.p.it.tks.rent.applicationports.adapters.driven;

import pl.lodz.p.it.tks.rent.applicationports.converters.UserConverter;
import pl.lodz.p.it.tks.rent.applicationports.exception.RepositoryAdapterException;
import pl.lodz.p.it.tks.rent.applicationports.infrastructure.user.admin.*;
import pl.lodz.p.it.tks.rent.data.user.AdminEnt;
import pl.lodz.p.it.tks.rent.domainmodel.user.Admin;
import pl.lodz.p.it.tks.rent.applicationports.infrastructure.user.admin.*;
import pl.lodz.p.it.tks.rent.applicationports.infrastructure.user.admin.*;
import pl.lodz.p.it.tks.rent.repository.UserEntRepository;
import pl.lodz.p.it.tks.rent.repository.exception.RepositoryEntException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@ApplicationScoped
public class AdminRepositoryAdapter implements AddAdminPort, UpdateAdminPort, GetAllAdminPort,
        GetAdminByLoginPort, GetAdminByIdPort {

    private final UserEntRepository userEntRepository;

    @Inject
    public AdminRepositoryAdapter(UserEntRepository userEntRepository) {
        this.userEntRepository = userEntRepository;
    }

    @Override
    public Admin add(Admin admin) throws RepositoryAdapterException {
        AdminEnt adminEnt = UserConverter.convertDomainToEnt(admin);
        try {
            return UserConverter.convertEntToDomain((AdminEnt) userEntRepository.add(adminEnt));
        } catch (RepositoryEntException e) {
            throw new RepositoryAdapterException(e);
        }
    }

    @Override
    public Admin get(UUID uuid) throws RepositoryAdapterException {
        try {
            return UserConverter.convertEntToDomain((AdminEnt) userEntRepository.get(uuid));
        } catch (RepositoryEntException e) {
            throw new RepositoryAdapterException(e);
        }
    }

    @Override
    public Admin get(String login) throws RepositoryAdapterException {
        try {
            return UserConverter.convertEntToDomain((AdminEnt) userEntRepository.get(login));
        } catch (RepositoryEntException e) {
            throw new RepositoryAdapterException(e);
        }
    }

    @Override
    public List<Admin> getAll() {
        return userEntRepository.getAll().stream()
                .filter(x -> x instanceof AdminEnt)
                .map(x -> UserConverter.convertEntToDomain((AdminEnt) x))
                .collect(Collectors.toList());
    }

    @Override
    public Admin update(Admin admin) throws RepositoryAdapterException {
        AdminEnt adminEnt = UserConverter.convertDomainToEnt(admin);
        try {
            return UserConverter.convertEntToDomain((AdminEnt) userEntRepository.update(adminEnt));
        } catch (RepositoryEntException e) {
            throw new RepositoryAdapterException(e);
        }
    }
}

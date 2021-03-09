package pl.lodz.p.it.tks.applicationports.infrastructure.user;

import pl.lodz.p.it.tks.applicationports.exception.RepositoryAdapterException;
import pl.lodz.p.it.tks.domainmodel.user.Admin;

import java.util.UUID;

public interface GetAdminByIdPort {
    Admin get(UUID uuid) throws RepositoryAdapterException;
}

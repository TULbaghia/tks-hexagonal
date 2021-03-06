package pl.lodz.p.it.tks.rent.applicationports.infrastructure.user.admin;

import pl.lodz.p.it.tks.rent.applicationports.exception.RepositoryAdapterException;
import pl.lodz.p.it.tks.rent.domainmodel.user.Admin;

import java.util.UUID;

public interface GetAdminByIdPort {
    Admin get(UUID uuid) throws RepositoryAdapterException;
}

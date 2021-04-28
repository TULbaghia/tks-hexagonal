package pl.lodz.p.it.tks.rent.applicationports.infrastructure.user.admin;

import pl.lodz.p.it.tks.rent.applicationports.exception.RepositoryAdapterException;
import pl.lodz.p.it.tks.rent.domainmodel.user.Admin;

public interface GetAdminByLoginPort {
    Admin get(String login) throws RepositoryAdapterException;
}

package pl.lodz.p.it.tks.applicationports.infrastructure.user.admin;

import pl.lodz.p.it.tks.applicationports.exception.RepositoryAdapterException;
import pl.lodz.p.it.tks.domainmodel.user.Admin;
import pl.lodz.p.it.tks.domainmodel.user.User;

public interface GetAdminByLoginPort {
    Admin get(String login) throws RepositoryAdapterException;
}

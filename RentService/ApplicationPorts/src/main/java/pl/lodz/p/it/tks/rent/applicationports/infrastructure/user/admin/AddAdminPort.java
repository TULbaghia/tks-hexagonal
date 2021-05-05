package pl.lodz.p.it.tks.rent.applicationports.infrastructure.user.admin;

import pl.lodz.p.it.tks.rent.applicationports.exception.RepositoryAdapterException;
import pl.lodz.p.it.tks.rent.domainmodel.user.Admin;

public interface AddAdminPort {
    Admin add(Admin admin) throws RepositoryAdapterException;
}

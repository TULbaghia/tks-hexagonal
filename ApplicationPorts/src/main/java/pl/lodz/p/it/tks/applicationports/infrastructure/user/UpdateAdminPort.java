package pl.lodz.p.it.tks.applicationports.infrastructure.user;

import lombok.NonNull;
import pl.lodz.p.it.tks.applicationports.exception.RepositoryAdapterException;
import pl.lodz.p.it.tks.domainmodel.user.Admin;

public interface UpdateAdminPort {
    void update(Admin admin) throws RepositoryAdapterException;
}

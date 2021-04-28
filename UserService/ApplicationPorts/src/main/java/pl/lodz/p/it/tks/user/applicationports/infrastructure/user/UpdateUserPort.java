package pl.lodz.p.it.tks.user.applicationports.infrastructure.user;

import pl.lodz.p.it.tks.user.applicationports.exception.RepositoryAdapterException;
import pl.lodz.p.it.tks.user.domainmodel.user.User;

public interface UpdateUserPort {
    User update(User admin) throws RepositoryAdapterException;
}

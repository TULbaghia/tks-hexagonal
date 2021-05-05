package pl.lodz.p.it.tks.user.applicationports.infrastructure.user;

import pl.lodz.p.it.tks.user.applicationports.exception.RepositoryAdapterException;
import pl.lodz.p.it.tks.user.domainmodel.user.User;

import java.util.UUID;

public interface GetUserByIdPort {
    User get(UUID uuid) throws RepositoryAdapterException;
}

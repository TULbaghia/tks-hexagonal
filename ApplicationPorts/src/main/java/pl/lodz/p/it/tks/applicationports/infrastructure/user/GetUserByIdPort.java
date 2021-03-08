package pl.lodz.p.it.tks.applicationports.infrastructure.user;

import pl.lodz.p.it.tks.domainmodel.user.User;

import java.util.UUID;

public interface GetUserByIdPort {
    User get(UUID uuid);
}

package pl.lodz.p.it.tks.applicationports.infrastructure.user;

import pl.lodz.p.it.tks.domainmodel.user.User;

public interface GetUserByLoginPort {
    User get(String login);
}

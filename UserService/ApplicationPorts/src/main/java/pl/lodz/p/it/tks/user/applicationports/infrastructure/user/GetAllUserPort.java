package pl.lodz.p.it.tks.user.applicationports.infrastructure.user;

import pl.lodz.p.it.tks.user.domainmodel.user.User;

import java.util.List;

public interface GetAllUserPort {
    List<User> getAll();
}

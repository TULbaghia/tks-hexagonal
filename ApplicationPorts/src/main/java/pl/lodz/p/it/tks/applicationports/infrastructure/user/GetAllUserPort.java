package pl.lodz.p.it.tks.applicationports.infrastructure.user;

import pl.lodz.p.it.tks.domainmodel.user.User;

import java.util.List;

public interface GetAllUserPort {
    List<User> getAll();
}
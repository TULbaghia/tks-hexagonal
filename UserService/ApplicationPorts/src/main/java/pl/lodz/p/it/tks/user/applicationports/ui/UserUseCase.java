package pl.lodz.p.it.tks.user.applicationports.ui;

import fish.payara.cluster.Clustered;
import pl.lodz.p.it.tks.user.applicationports.exception.RepositoryAdapterException;
import pl.lodz.p.it.tks.user.domainmodel.user.User;


import javax.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.UUID;

public interface UserUseCase {
    User add(User user) throws RepositoryAdapterException;

    User update(User user) throws RepositoryAdapterException;

    List<User> getAll();

    User get(UUID uuid) throws RepositoryAdapterException;

    User get(String login) throws RepositoryAdapterException;

    void delete(UUID uuid) throws RepositoryAdapterException;
}

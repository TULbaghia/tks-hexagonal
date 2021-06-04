package pl.lodz.p.it.tks.user.applicationports.adapters.driven;

import fish.payara.cluster.Clustered;
import lombok.NoArgsConstructor;
import pl.lodz.p.it.tks.user.applicationports.converters.UserConverter;
import pl.lodz.p.it.tks.user.applicationports.exception.RepositoryAdapterException;
import pl.lodz.p.it.tks.user.applicationports.infrastructure.user.*;
import pl.lodz.p.it.tks.user.data.user.UserEnt;
import pl.lodz.p.it.tks.user.domainmodel.user.User;
import pl.lodz.p.it.tks.user.repository.UserEntRepository;
import pl.lodz.p.it.tks.user.repository.exception.RepositoryEntException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@ApplicationScoped
public class UserRepositoryAdapter implements Serializable, AddUserPort, UpdateUserPort, GetAllUserPort,
        GetUserByLoginPort, GetUserByIdPort, DeleteUserPort {

    @Inject
    private UserEntRepository userEntRepository;

    @Inject
    public UserRepositoryAdapter(UserEntRepository userEntRepository) {
        this.userEntRepository = userEntRepository;
    }

    @Override
    public User add(User user) throws RepositoryAdapterException {
        UserEnt userEnt = UserConverter.toEnt(user);
        try {
            return UserConverter.toDomain(userEntRepository.add(userEnt));
        } catch (RepositoryEntException e) {
            throw new RepositoryAdapterException(e);
        }
    }

    @Override
    public User get(UUID uuid) throws RepositoryAdapterException {
        try {
            return UserConverter.toDomain(userEntRepository.get(uuid));
        } catch (RepositoryEntException e) {
            throw new RepositoryAdapterException(e);
        }
    }

    @Override
    public User get(String login) throws RepositoryAdapterException {
        try {
            return UserConverter.toDomain(userEntRepository.get(login));
        } catch (RepositoryEntException e) {
            throw new RepositoryAdapterException(e);
        }
    }

    @Override
    public List<User> getAll() {
        return userEntRepository.getAll().stream()
                .map(UserConverter::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public User update(User user) throws RepositoryAdapterException {
        UserEnt userEnt = UserConverter.toEnt(user);
        try {
            return UserConverter.toDomain(userEntRepository.update(userEnt));
        } catch (RepositoryEntException e) {
            throw new RepositoryAdapterException(e);
        }
    }

    @Override
    public void delete(UUID uuid) throws RepositoryAdapterException {
        try {
            userEntRepository.delete(uuid);
        } catch (RepositoryEntException e) {
            throw new RepositoryAdapterException(e);
        }
    }
}

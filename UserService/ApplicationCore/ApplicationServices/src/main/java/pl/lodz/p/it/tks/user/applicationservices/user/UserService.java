package pl.lodz.p.it.tks.user.applicationservices.user;

import pl.lodz.p.it.tks.user.applicationports.exception.RepositoryAdapterException;
import pl.lodz.p.it.tks.user.applicationports.infrastructure.user.*;
import pl.lodz.p.it.tks.user.applicationports.ui.UserUseCase;
import pl.lodz.p.it.tks.user.domainmodel.user.User;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class UserService implements UserUseCase {
    private final AddUserPort addUserPort;
    private final UpdateUserPort updateUserPort;
    private final GetAllUserPort getAllUserPort;
    private final GetUserByIdPort getUserByIdPort;
    private final GetUserByLoginPort getUserByLoginPort;

    @Inject
    public UserService(AddUserPort addUserPort, UpdateUserPort updateUserPort, GetAllUserPort getAllUserPort, GetUserByIdPort getUserByIdPort, GetUserByLoginPort getUserByLoginPort) {
        this.addUserPort = addUserPort;
        this.updateUserPort = updateUserPort;
        this.getAllUserPort = getAllUserPort;
        this.getUserByIdPort = getUserByIdPort;
        this.getUserByLoginPort = getUserByLoginPort;
    }

    @Override
    public User add(User user) throws RepositoryAdapterException {
        checkPassword(user.getPassword());
        if (getAll().stream().anyMatch(x -> x.getLogin().equals(user.getLogin()))) {
            throw new RepositoryAdapterException("User with this login already exists.");
        }
        return addUserPort.add(user);
    }

    @Override
    public User update(User user) throws RepositoryAdapterException {
        checkPassword(user.getPassword());
        if (getAll().stream().anyMatch(x -> x.getLogin().equals(user.getLogin()) && !x.getId().equals(user.getId()))) {
            throw new RepositoryAdapterException("User with this login already exists.");
        }
        return updateUserPort.update(user);
    }

    @Override
    public List<User> getAll() {
        return getAllUserPort.getAll();
    }

    @Override
    public User get(UUID uuid) throws RepositoryAdapterException {
        return getUserByIdPort.get(uuid);
    }

    @Override
    public User get(String login) throws RepositoryAdapterException {
        return getUserByLoginPort.get(login);
    }

    private void checkPassword(String password) throws RepositoryAdapterException {
        if (!password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$")) {
            throw new RepositoryAdapterException("Password is not secure.");
        }
    }
}

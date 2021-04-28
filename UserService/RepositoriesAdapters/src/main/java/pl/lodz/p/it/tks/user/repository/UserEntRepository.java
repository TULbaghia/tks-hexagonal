package pl.lodz.p.it.tks.user.repository;

import pl.lodz.p.it.tks.user.data.user.UserEnt;
import pl.lodz.p.it.tks.user.data.user.UserTypeEnt;
import pl.lodz.p.it.tks.user.repository.exception.RepositoryEntException;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class UserEntRepository extends EntRepository<UserEnt> {
    @Override
    public synchronized UserEnt add(UserEnt item) throws RepositoryEntException {
        if (getAll().stream().anyMatch(x -> x.getLogin().equals(item.getLogin()))) {
            throw new RepositoryEntException("User with this login already exists.");
        }
        return super.add(item);
    }

    @Override
    public UserEnt get(UUID id) throws RepositoryEntException {
        return super.get(id);
    }

    public UserEnt get(String login) throws RepositoryEntException {
        return getAll().stream().filter(x -> x.getLogin().equals(login)).findFirst().orElseThrow(() -> new RepositoryEntException("Item does not exist."));
    }

    @Override
    public List<UserEnt> getAll() {
        return super.getAll();
    }

    @Override
    public synchronized UserEnt update(UserEnt item) throws RepositoryEntException {
        return super.update(item);
    }

    @PostConstruct
    private void postConstruct() {
        try {
            add(UserEnt.builder().firstname("TestUser").lastname("TestUser").login("TestUser").password("zaq1@WSX").userTypeEnt(UserTypeEnt.ADMIN).build());
        } catch (RepositoryEntException e) {
            e.printStackTrace();
        }
    }
}

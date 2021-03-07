package pl.lodz.p.it.tks.data.repository;

import lombok.NonNull;
import pl.lodz.p.it.tks.data.model.user.UserEnt;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class UserEntRepository extends Repository<UserEnt> {

    public UserEnt get(String login) {
        return filter(x -> x.getLogin().equals(login)).stream().findFirst().orElse(null);
    }

    public List<UserEnt> getAll(boolean active) {
        return filter(x -> x.isActive() == active);
    }

    @Override
    public synchronized void add(@NonNull UserEnt item) throws RepositoryException {
        if (get(item.getLogin()) != null) {
            throw new RepositoryException("userAlreadyExists");
        }
        super.add(item);
    }

    @Override
    public synchronized void update(@NonNull UserEnt item) throws RepositoryException {
        if(filter(x -> x.getLogin().equals(item.getLogin()) && !x.getId().equals(item.getId())).size() > 0) {
            throw new RepositoryException("loginAlreadyExists");
        }
        super.update(item);
    }

    @Override
    public synchronized void delete(@NonNull UUID id) throws RepositoryException {
        throw new RepositoryException("cannotDeleteUser");
    }
}

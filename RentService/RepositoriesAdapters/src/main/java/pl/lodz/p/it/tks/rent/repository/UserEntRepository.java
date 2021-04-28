package pl.lodz.p.it.tks.rent.repository;

import pl.lodz.p.it.tks.rent.data.user.AdminEnt;
import pl.lodz.p.it.tks.rent.data.user.CustomerEnt;
import pl.lodz.p.it.tks.rent.data.user.EmployeeEnt;
import pl.lodz.p.it.tks.rent.data.user.UserEnt;
import pl.lodz.p.it.tks.rent.repository.exception.RepositoryEntException;

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
    public void loadSampleData() {
        try {
            for (int i = 1; i < 5; i++) {
                add(CustomerEnt.builder()
                        .firstname("Customer" + i)
                        .lastname("Kowalski" + i)
                        .login("Klient" + i)
                        .password("zaq1@WSX")
                        .build()
                );
            }

            add(CustomerEnt.builder()
                    .firstname("TestCustomer")
                    .lastname("TestCustomer")
                    .login("TestCustomer")
                    .password("zaq1@WSX")
                    .build()
            );

            add(EmployeeEnt.builder()
                    .firstname("TestEmployee")
                    .lastname("TestEmployee")
                    .login("TestEmployee")
                    .password("zaq1@WSX")
                    .build()
            );

            add(AdminEnt.builder()
                    .firstname("TestAdmin")
                    .lastname("TestAdmin")
                    .login("TestAdmin")
                    .password("zaq1@WSX")
                    .build()
            );
        } catch (RepositoryEntException e) {
            e.printStackTrace();
        }
    }
}

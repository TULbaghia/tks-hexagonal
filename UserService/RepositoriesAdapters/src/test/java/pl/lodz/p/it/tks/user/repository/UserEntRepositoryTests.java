package pl.lodz.p.it.tks.user.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.lodz.p.it.tks.user.data.user.UserEnt;
import pl.lodz.p.it.tks.user.data.user.UserTypeEnt;
import pl.lodz.p.it.tks.user.repository.exception.RepositoryEntException;

import java.util.UUID;

public class UserEntRepositoryTests {

    private static UserEntRepository userEntRepository;

    @BeforeEach
    public void beforeTest() {
        userEntRepository = new UserEntRepository();
    }

    @Test
    public void addUserTest() throws RepositoryEntException {
        UserEnt userEnt = UserEnt.builder()
                .firstname("User")
                .lastname("Kowalski")
                .login("Klient")
                .password("zaq1@WSX")
                .userTypeEnt(UserTypeEnt.ADMIN)
                .build();

        userEntRepository.add(userEnt);
        Assertions.assertEquals(userEntRepository.getAll().size(), 1);
        Assertions.assertThrows(RepositoryEntException.class, () -> userEntRepository.add(userEnt));
        Assertions.assertEquals(userEntRepository.getAll().size(), 1);
    }

    @Test
    public void getUserByIdTest() throws RepositoryEntException {
        UserEnt userEnt = UserEnt.builder()
                .firstname("User")
                .lastname("Kowalski")
                .login("Klient")
                .password("zaq1@WSX")
                .userTypeEnt(UserTypeEnt.ADMIN)
                .build();

        userEntRepository.add(userEnt);
        Assertions.assertEquals(userEntRepository.getAll().size(), 1);
        Assertions.assertNotEquals(userEnt.getId(), null);

        UserEnt getCustomer = userEntRepository.get(userEnt.getId());
        Assertions.assertEquals(getCustomer.getId(), userEnt.getId());
        Assertions.assertEquals(getCustomer.isActive(), userEnt.isActive());
        Assertions.assertEquals(getCustomer.getFirstname(), userEnt.getFirstname());
        Assertions.assertEquals(getCustomer.getLastname(), userEnt.getLastname());
        Assertions.assertEquals(getCustomer.getLogin(), userEnt.getLogin());
        Assertions.assertEquals(getCustomer.getPassword(), userEnt.getPassword());
        Assertions.assertEquals(getCustomer.getUserTypeEnt(), userEnt.getUserTypeEnt());

        Assertions.assertThrows(RepositoryEntException.class, () -> userEntRepository.get(UUID.randomUUID()));
    }

    @Test
    public void getUserByLoginTest() throws RepositoryEntException {
        UserEnt userEnt = UserEnt.builder()
                .firstname("User")
                .lastname("Kowalski")
                .login("Klient")
                .password("zaq1@WSX")
                .userTypeEnt(UserTypeEnt.ADMIN)
                .build();

        userEntRepository.add(userEnt);
        Assertions.assertEquals(userEntRepository.getAll().size(), 1);
        Assertions.assertNotEquals(userEnt.getId(), null);

        UserEnt getCustomer = userEntRepository.get(userEnt.getLogin());
        Assertions.assertEquals(getCustomer.getId(), userEnt.getId());
        Assertions.assertEquals(getCustomer.isActive(), userEnt.isActive());
        Assertions.assertEquals(getCustomer.getFirstname(), userEnt.getFirstname());
        Assertions.assertEquals(getCustomer.getLastname(), userEnt.getLastname());
        Assertions.assertEquals(getCustomer.getLogin(), userEnt.getLogin());
        Assertions.assertEquals(getCustomer.getPassword(), userEnt.getPassword());
        Assertions.assertEquals(getCustomer.getUserTypeEnt(), userEnt.getUserTypeEnt());

        Assertions.assertThrows(RepositoryEntException.class, () -> userEntRepository.get("."));
    }

    @Test
    public void getAllUserTest() throws RepositoryEntException {
        int usersCount = 5;
        for (int i = 0; i < usersCount; i++) {
            UserEnt userEnt = UserEnt.builder()
                    .firstname("User" + i)
                    .lastname("Kowalski")
                    .login("Klient" + i)
                    .password("zaq1@WSX")
                    .userTypeEnt(UserTypeEnt.ADMIN)
                    .build();
            userEntRepository.add(userEnt);
        }

        Assertions.assertEquals(userEntRepository.getAll().size(), usersCount);
    }

    @Test
    public void updateUserTest() throws RepositoryEntException {
        UserEnt userEnt = UserEnt.builder()
                .firstname("User")
                .lastname("Kowalski")
                .login("Klient")
                .password("zaq1@WSX")
                .userTypeEnt(UserTypeEnt.CUSTOMER)
                .build();

        userEntRepository.add(userEnt);
        Assertions.assertEquals(userEntRepository.getAll().size(), 1);
        Assertions.assertNotEquals(userEnt.getId(), null);

        UserEnt updatedCustomer = UserEnt.builder()
                .id(userEnt.getId())
                .login(userEnt.getLogin())
                .firstname("User")
                .lastname("TestoweNazwisko")
                .password("zaq1@WSX")
                .userTypeEnt(UserTypeEnt.ADMIN)
                .build();

        userEntRepository.update(updatedCustomer);
        Assertions.assertEquals(userEntRepository.getAll().size(), 1);

        UserEnt getUpdatedCustomer = userEntRepository.get(userEnt.getId());
        Assertions.assertEquals(getUpdatedCustomer.getId(), updatedCustomer.getId());
        Assertions.assertEquals(getUpdatedCustomer.isActive(), updatedCustomer.isActive());
        Assertions.assertEquals(getUpdatedCustomer.getFirstname(), updatedCustomer.getFirstname());
        Assertions.assertEquals(getUpdatedCustomer.getLastname(), updatedCustomer.getLastname());
        Assertions.assertEquals(getUpdatedCustomer.getLogin(), updatedCustomer.getLogin());
        Assertions.assertEquals(getUpdatedCustomer.getPassword(), updatedCustomer.getPassword());
        Assertions.assertEquals(getUpdatedCustomer.getUserTypeEnt(), updatedCustomer.getUserTypeEnt());


        Assertions.assertThrows(RepositoryEntException.class, () -> userEntRepository.update(new UserEnt()));
    }
}


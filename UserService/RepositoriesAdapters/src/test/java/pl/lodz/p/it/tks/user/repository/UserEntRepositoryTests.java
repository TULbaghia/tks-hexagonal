package pl.lodz.p.it.tks.user.repository;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pl.lodz.p.it.tks.user.data.user.UserEnt;
import pl.lodz.p.it.tks.user.data.user.UserTypeEnt;
import pl.lodz.p.it.tks.user.repository.exception.RepositoryEntException;

import java.util.UUID;

public class UserEntRepositoryTests {

    private UserEntRepository userEntRepository;

    @BeforeMethod
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
        Assert.assertEquals(userEntRepository.getAll().size(), 1);
        Assert.assertThrows(RepositoryEntException.class, () -> userEntRepository.add(userEnt));
        Assert.assertEquals(userEntRepository.getAll().size(), 1);
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
        Assert.assertEquals(userEntRepository.getAll().size(), 1);
        Assert.assertNotEquals(userEnt.getId(), null);

        UserEnt getCustomer = userEntRepository.get(userEnt.getId());
        Assert.assertEquals(getCustomer.getId(), userEnt.getId());
        Assert.assertEquals(getCustomer.isActive(), userEnt.isActive());
        Assert.assertEquals(getCustomer.getFirstname(), userEnt.getFirstname());
        Assert.assertEquals(getCustomer.getLastname(), userEnt.getLastname());
        Assert.assertEquals(getCustomer.getLogin(), userEnt.getLogin());
        Assert.assertEquals(getCustomer.getPassword(), userEnt.getPassword());
        Assert.assertEquals(getCustomer.getUserTypeEnt(), userEnt.getUserTypeEnt());

        Assert.assertThrows(RepositoryEntException.class, () -> userEntRepository.get(UUID.randomUUID()));
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
        Assert.assertEquals(userEntRepository.getAll().size(), 1);
        Assert.assertNotEquals(userEnt.getId(), null);

        UserEnt getCustomer = userEntRepository.get(userEnt.getLogin());
        Assert.assertEquals(getCustomer.getId(), userEnt.getId());
        Assert.assertEquals(getCustomer.isActive(), userEnt.isActive());
        Assert.assertEquals(getCustomer.getFirstname(), userEnt.getFirstname());
        Assert.assertEquals(getCustomer.getLastname(), userEnt.getLastname());
        Assert.assertEquals(getCustomer.getLogin(), userEnt.getLogin());
        Assert.assertEquals(getCustomer.getPassword(), userEnt.getPassword());
        Assert.assertEquals(getCustomer.getUserTypeEnt(), userEnt.getUserTypeEnt());

        Assert.assertThrows(RepositoryEntException.class, () -> userEntRepository.get("."));
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

        Assert.assertEquals(userEntRepository.getAll().size(), usersCount);
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
        Assert.assertEquals(userEntRepository.getAll().size(), 1);
        Assert.assertNotEquals(userEnt.getId(), null);

        UserEnt updatedCustomer = UserEnt.builder()
                .id(userEnt.getId())
                .login(userEnt.getLogin())
                .firstname("User")
                .lastname("TestoweNazwisko")
                .password("zaq1@WSX")
                .userTypeEnt(UserTypeEnt.ADMIN)
                .build();

        userEntRepository.update(updatedCustomer);
        Assert.assertEquals(userEntRepository.getAll().size(), 1);

        UserEnt getUpdatedCustomer = userEntRepository.get(userEnt.getId());
        Assert.assertEquals(getUpdatedCustomer.getId(), updatedCustomer.getId());
        Assert.assertEquals(getUpdatedCustomer.isActive(), updatedCustomer.isActive());
        Assert.assertEquals(getUpdatedCustomer.getFirstname(), updatedCustomer.getFirstname());
        Assert.assertEquals(getUpdatedCustomer.getLastname(), updatedCustomer.getLastname());
        Assert.assertEquals(getUpdatedCustomer.getLogin(), updatedCustomer.getLogin());
        Assert.assertEquals(getUpdatedCustomer.getPassword(), updatedCustomer.getPassword());
        Assert.assertEquals(getUpdatedCustomer.getUserTypeEnt(), updatedCustomer.getUserTypeEnt());


        Assert.assertThrows(RepositoryEntException.class, () -> userEntRepository.update(new UserEnt()));
    }
}


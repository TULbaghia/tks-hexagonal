package pl.lodz.p.it.tks.user.applicationports.adapters;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pl.lodz.p.it.tks.user.applicationports.adapters.driven.UserRepositoryAdapter;
import pl.lodz.p.it.tks.user.applicationports.exception.RepositoryAdapterException;
import pl.lodz.p.it.tks.user.data.user.UserEnt;
import pl.lodz.p.it.tks.user.data.user.UserTypeEnt;
import pl.lodz.p.it.tks.user.domainmodel.user.User;
import pl.lodz.p.it.tks.user.domainmodel.user.UserType;
import pl.lodz.p.it.tks.user.repository.UserEntRepository;
import pl.lodz.p.it.tks.user.repository.exception.RepositoryEntException;

import java.lang.reflect.Field;

public class UserRepositoryAdapterTests {

    private UserRepositoryAdapter UserRepositoryAdapter;
    private UserEntRepository userEntRepository;

    @BeforeMethod
    public void beforeTest() throws IllegalAccessException, NoSuchFieldException {
        UserRepositoryAdapter = new UserRepositoryAdapter(new UserEntRepository());
        userEntRepository = new UserEntRepository();
        Field field = UserRepositoryAdapter.getClass().getDeclaredField("userEntRepository");
        field.setAccessible(true);
        field.set(UserRepositoryAdapter, userEntRepository);
    }

    @Test
    public void addUserTest() throws RepositoryAdapterException, RepositoryEntException {
        User user = User.builder()
                .firstname("Customer")
                .lastname("Kowalski")
                .login("Klient")
                .password("zaq1@WSX")
                .userType(UserType.ADMIN)
                .build();

        Assert.assertEquals(userEntRepository.getAll().size(), 0);
        UserRepositoryAdapter.add(user);
        Assert.assertEquals(userEntRepository.getAll().size(), 1);
        Assert.assertNotSame(userEntRepository.get("Klient"), user);
    }

    @Test
    public void getUserByIdTest() throws RepositoryAdapterException, RepositoryEntException {
        UserEnt user = UserEnt.builder()
                .firstname("Customer")
                .lastname("Kowalski")
                .login("Klient")
                .password("zaq1@WSX")
                .userTypeEnt(UserTypeEnt.ADMIN)
                .build();

        Assert.assertEquals(userEntRepository.getAll().size(), 0);
        userEntRepository.add(user);
        Assert.assertEquals(userEntRepository.getAll().size(), 1);

        User getUser = UserRepositoryAdapter.get(user.getId());
        Assert.assertNotSame(getUser, user);
        Assert.assertEquals(getUser.getId(), user.getId());
        Assert.assertEquals(getUser.isActive(), user.isActive());
        Assert.assertEquals(getUser.getFirstname(), user.getFirstname());
        Assert.assertEquals(getUser.getLastname(), user.getLastname());
        Assert.assertEquals(getUser.getLogin(), user.getLogin());
        Assert.assertEquals(getUser.getPassword(), user.getPassword());
    }

    @Test
    public void getUserByLoginTest() throws RepositoryAdapterException, RepositoryEntException {
        UserEnt user = UserEnt.builder()
                .firstname("Customer")
                .lastname("Kowalski")
                .login("Klient")
                .password("zaq1@WSX")
                .userTypeEnt(UserTypeEnt.ADMIN)
                .build();

        Assert.assertEquals(userEntRepository.getAll().size(), 0);
        userEntRepository.add(user);
        Assert.assertEquals(userEntRepository.getAll().size(), 1);

        User getUser = UserRepositoryAdapter.get("Klient");
        Assert.assertNotSame(getUser, user);
        Assert.assertEquals(getUser.getId(), user.getId());
        Assert.assertEquals(getUser.isActive(), user.isActive());
        Assert.assertEquals(getUser.getFirstname(), user.getFirstname());
        Assert.assertEquals(getUser.getLastname(), user.getLastname());
        Assert.assertEquals(getUser.getLogin(), user.getLogin());
        Assert.assertEquals(getUser.getPassword(), user.getPassword());
    }

    @Test
    public void getAllUser() throws RepositoryEntException, RepositoryAdapterException {
        int usersCount = 5;
        for (int i = 0; i < usersCount; i++) {
            UserEnt userEnt = UserEnt.builder()
                    .firstname("Customer" + i)
                    .lastname("Kowalski")
                    .login("Klient" + i)
                    .password("zaq1@WSX")
                    .userTypeEnt(UserTypeEnt.ADMIN)
                    .build();
            userEntRepository.add(userEnt);
        }

        Assert.assertEquals(UserRepositoryAdapter.getAll().size(), usersCount);
    }

    @Test
    public void updateUser() throws RepositoryEntException, RepositoryAdapterException {
        UserEnt userEnt  = UserEnt.builder()
                .firstname("Customer")
                .lastname("Kowalski")
                .login("Klient")
                .password("zaq1@WSX")
                .userTypeEnt(UserTypeEnt.ADMIN)
                .build();

        userEntRepository.add(userEnt);
        Assert.assertEquals(userEntRepository.getAll().size(), 1);
        Assert.assertNotEquals(userEnt.getId(), null);

        User updatedUser = User.builder()
                .id(userEnt.getId())
                .login(userEnt.getLogin())
                .firstname("User")
                .lastname("TestoweNazwisko")
                .password("zaq1@WSX")
                .userType(UserType.ADMIN)
                .build();

        UserRepositoryAdapter.update(updatedUser);
        Assert.assertEquals(userEntRepository.getAll().size(), 1);

        UserEnt getUpdatedUser = (UserEnt) userEntRepository.get(userEnt.getId());
        Assert.assertEquals(getUpdatedUser.getId(), updatedUser.getId());
        Assert.assertEquals(getUpdatedUser.isActive(), updatedUser.isActive());
        Assert.assertEquals(getUpdatedUser.getFirstname(), updatedUser.getFirstname());
        Assert.assertEquals(getUpdatedUser.getLastname(), updatedUser.getLastname());
        Assert.assertEquals(getUpdatedUser.getLogin(), updatedUser.getLogin());
        Assert.assertEquals(getUpdatedUser.getPassword(), updatedUser.getPassword());

        User updatedUser2 = User.builder()
                .login(userEnt.getLogin())
                .firstname("User")
                .lastname("TestoweNazwisko")
                .password("zaq1@WSX")
                .userType(UserType.ADMIN)
                .build();

        Assert.assertThrows(RepositoryAdapterException.class, () -> UserRepositoryAdapter.update(updatedUser2));
    }
}

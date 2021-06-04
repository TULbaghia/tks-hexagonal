package pl.lodz.p.it.tks.user.applicationports.adapters;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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

    private static UserRepositoryAdapter UserRepositoryAdapter;
    private static UserEntRepository userEntRepository;

    @BeforeEach
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

        Assertions.assertEquals(userEntRepository.getAll().size(), 0);
        UserRepositoryAdapter.add(user);
        Assertions.assertEquals(userEntRepository.getAll().size(), 1);
        Assertions.assertNotSame(userEntRepository.get("Klient"), user);
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

        Assertions.assertEquals(userEntRepository.getAll().size(), 0);
        userEntRepository.add(user);
        Assertions.assertEquals(userEntRepository.getAll().size(), 1);

        User getUser = UserRepositoryAdapter.get(user.getId());
        Assertions.assertNotSame(getUser, user);
        Assertions.assertEquals(getUser.getId(), user.getId());
        Assertions.assertEquals(getUser.isActive(), user.isActive());
        Assertions.assertEquals(getUser.getFirstname(), user.getFirstname());
        Assertions.assertEquals(getUser.getLastname(), user.getLastname());
        Assertions.assertEquals(getUser.getLogin(), user.getLogin());
        Assertions.assertEquals(getUser.getPassword(), user.getPassword());
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

        Assertions.assertEquals(userEntRepository.getAll().size(), 0);
        userEntRepository.add(user);
        Assertions.assertEquals(userEntRepository.getAll().size(), 1);

        User getUser = UserRepositoryAdapter.get("Klient");
        Assertions.assertNotSame(getUser, user);
        Assertions.assertEquals(getUser.getId(), user.getId());
        Assertions.assertEquals(getUser.isActive(), user.isActive());
        Assertions.assertEquals(getUser.getFirstname(), user.getFirstname());
        Assertions.assertEquals(getUser.getLastname(), user.getLastname());
        Assertions.assertEquals(getUser.getLogin(), user.getLogin());
        Assertions.assertEquals(getUser.getPassword(), user.getPassword());
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

        Assertions.assertEquals(UserRepositoryAdapter.getAll().size(), usersCount);
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
        Assertions.assertEquals(userEntRepository.getAll().size(), 1);
        Assertions.assertNotEquals(userEnt.getId(), null);

        User updatedUser = User.builder()
                .id(userEnt.getId())
                .login(userEnt.getLogin())
                .firstname("User")
                .lastname("TestoweNazwisko")
                .password("zaq1@WSX")
                .userType(UserType.ADMIN)
                .build();

        UserRepositoryAdapter.update(updatedUser);
        Assertions.assertEquals(userEntRepository.getAll().size(), 1);

        UserEnt getUpdatedUser = (UserEnt) userEntRepository.get(userEnt.getId());
        Assertions.assertEquals(getUpdatedUser.getId(), updatedUser.getId());
        Assertions.assertEquals(getUpdatedUser.isActive(), updatedUser.isActive());
        Assertions.assertEquals(getUpdatedUser.getFirstname(), updatedUser.getFirstname());
        Assertions.assertEquals(getUpdatedUser.getLastname(), updatedUser.getLastname());
        Assertions.assertEquals(getUpdatedUser.getLogin(), updatedUser.getLogin());
        Assertions.assertEquals(getUpdatedUser.getPassword(), updatedUser.getPassword());

        User updatedUser2 = User.builder()
                .login(userEnt.getLogin())
                .firstname("User")
                .lastname("TestoweNazwisko")
                .password("zaq1@WSX")
                .userType(UserType.ADMIN)
                .build();

        Assertions.assertThrows(RepositoryAdapterException.class, () -> UserRepositoryAdapter.update(updatedUser2));
    }
}

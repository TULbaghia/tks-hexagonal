package pl.lodz.p.it.tks.user.applicationservices;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.lodz.p.it.tks.user.applicationports.adapters.driven.UserRepositoryAdapter;
import pl.lodz.p.it.tks.user.applicationports.exception.RepositoryAdapterException;
import pl.lodz.p.it.tks.user.applicationservices.user.UserService;
import pl.lodz.p.it.tks.user.domainmodel.user.User;
import pl.lodz.p.it.tks.user.domainmodel.user.UserType;
import pl.lodz.p.it.tks.user.repository.UserEntRepository;

import java.util.List;

public class UserServiceTest {

    private static UserService userService;
    private static UserRepositoryAdapter userRepositoryAdapter;

    @BeforeEach
    public void beforeMethod() {
        userRepositoryAdapter = new UserRepositoryAdapter(new UserEntRepository());
        userService = new UserService(userRepositoryAdapter, userRepositoryAdapter, userRepositoryAdapter, userRepositoryAdapter, userRepositoryAdapter, userRepositoryAdapter);
    }

    @Test
    public void addUserTest() throws RepositoryAdapterException {
        userService.add(User.builder().firstname("Jan").lastname("User").password("zaq1@WSX").login("user").userType(UserType.ADMIN).build());
        Assertions.assertThrows(RepositoryAdapterException.class, () ->
                userService.add(User.builder().firstname("Jan").lastname("User").password("zaq1@WSX").login("user").userType(UserType.ADMIN).build()));

        Assertions.assertEquals(userRepositoryAdapter.get("user").getLogin(), "user");
        Assertions.assertEquals(userRepositoryAdapter.get("user").getLastname(), "User");
        Assertions.assertEquals(userRepositoryAdapter.get("user").getFirstname(), "Jan");
        Assertions.assertEquals(userRepositoryAdapter.get("user").getPassword(), "zaq1@WSX");
    }

    @Test
    public void updateUserTest() throws RepositoryAdapterException {
        userService.add(User.builder().firstname("Jan").lastname("User").password("zaq1@WSX").login("user").userType(UserType.ADMIN).build());

        User old = userService.get("user");
        old.setFirstname("T1");
        old.setLastname("T2");
        old.setPassword("ZAQ!2wsx");

        User update = userService.update(old);

        Assertions.assertEquals(update.getFirstname(), "T1");
        Assertions.assertEquals(update.getLastname(), "T2");
        Assertions.assertEquals(update.getPassword(), "ZAQ!2wsx");

        Assertions.assertEquals(userRepositoryAdapter.get("user").getFirstname(), "T1");
        Assertions.assertEquals(userRepositoryAdapter.get("user").getLastname(), "T2");
        Assertions.assertEquals(userRepositoryAdapter.get("user").getPassword(), "ZAQ!2wsx");
    }

    @Test
    public void getAllUserTest() throws RepositoryAdapterException {
        userService.add(User.builder().firstname("Jan").lastname("User").password("zaq1@WSX").login("user1").userType(UserType.ADMIN).build());
        userService.add(User.builder().firstname("Jan").lastname("User").password("zaq1@WSX").login("user2").userType(UserType.ADMIN).build());
        userService.add(User.builder().firstname("Jan").lastname("User").password("zaq1@WSX").login("user3").userType(UserType.ADMIN).build());

        List<User> userList = userService.getAll();

        Assertions.assertEquals(userList.size(), 3);
        Assertions.assertEquals(userList.get(0).getLogin(), "user1");
        Assertions.assertEquals(userList.get(1).getLogin(), "user2");
        Assertions.assertEquals(userList.get(2).getLogin(), "user3");

    }

    @Test
    public void getByUUIDUserTest() throws RepositoryAdapterException {
        User a = userService.add(User.builder().firstname("Jan").lastname("User").password("zaq1@WSX").login("user1").userType(UserType.ADMIN).build());

        User get = userService.get(a.getId());

        Assertions.assertEquals(get, a);
    }

    @Test
    public void getByLoginUserTest() throws RepositoryAdapterException {
        User a = userService.add(User.builder().firstname("Jan").lastname("User").password("zaq1@WSX").login("user1").userType(UserType.ADMIN).build());

        User get = userService.get(a.getLogin());

        Assertions.assertEquals(get, a);
    }
}

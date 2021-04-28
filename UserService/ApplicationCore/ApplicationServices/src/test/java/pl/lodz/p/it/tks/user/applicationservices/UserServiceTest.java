package pl.lodz.p.it.tks.user.applicationservices;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pl.lodz.p.it.tks.user.applicationports.adapters.driven.UserRepositoryAdapter;
import pl.lodz.p.it.tks.user.applicationports.exception.RepositoryAdapterException;
import pl.lodz.p.it.tks.user.applicationservices.user.UserService;
import pl.lodz.p.it.tks.user.domainmodel.user.User;
import pl.lodz.p.it.tks.user.domainmodel.user.UserType;
import pl.lodz.p.it.tks.user.repository.UserEntRepository;

import java.util.List;

public class UserServiceTest {

    private UserService userService;
    private UserRepositoryAdapter userRepositoryAdapter;

    @BeforeMethod
    public void beforeMethod() {
        userRepositoryAdapter = new UserRepositoryAdapter(new UserEntRepository());
        userService = new UserService(userRepositoryAdapter, userRepositoryAdapter, userRepositoryAdapter, userRepositoryAdapter, userRepositoryAdapter);
    }

    @Test
    public void addUserTest() throws RepositoryAdapterException {
        userService.add(User.builder().firstname("Jan").lastname("User").password("zaq1@WSX").login("user").userType(UserType.ADMIN).build());
        Assert.assertThrows(RepositoryAdapterException.class, () ->
                userService.add(User.builder().firstname("Jan").lastname("User").password("zaq1@WSX").login("user").userType(UserType.ADMIN).build()));

        Assert.assertEquals(userRepositoryAdapter.get("user").getLogin(), "user");
        Assert.assertEquals(userRepositoryAdapter.get("user").getLastname(), "User");
        Assert.assertEquals(userRepositoryAdapter.get("user").getFirstname(), "Jan");
        Assert.assertEquals(userRepositoryAdapter.get("user").getPassword(), "zaq1@WSX");
    }

    @Test
    public void updateUserTest() throws RepositoryAdapterException {
        userService.add(User.builder().firstname("Jan").lastname("User").password("zaq1@WSX").login("user").userType(UserType.ADMIN).build());

        User old = userService.get("user");
        old.setFirstname("T1");
        old.setLastname("T2");
        old.setPassword("ZAQ!2wsx");

        User update = userService.update(old);

        Assert.assertEquals(update.getFirstname(), "T1");
        Assert.assertEquals(update.getLastname(), "T2");
        Assert.assertEquals(update.getPassword(), "ZAQ!2wsx");

        Assert.assertEquals(userRepositoryAdapter.get("user").getFirstname(), "T1");
        Assert.assertEquals(userRepositoryAdapter.get("user").getLastname(), "T2");
        Assert.assertEquals(userRepositoryAdapter.get("user").getPassword(), "ZAQ!2wsx");
    }

    @Test
    public void getAllUserTest() throws RepositoryAdapterException {
        userService.add(User.builder().firstname("Jan").lastname("User").password("zaq1@WSX").login("user1").userType(UserType.ADMIN).build());
        userService.add(User.builder().firstname("Jan").lastname("User").password("zaq1@WSX").login("user2").userType(UserType.ADMIN).build());
        userService.add(User.builder().firstname("Jan").lastname("User").password("zaq1@WSX").login("user3").userType(UserType.ADMIN).build());

        List<User> userList = userService.getAll();

        Assert.assertEquals(userList.size(), 3);
        Assert.assertEquals(userList.get(0).getLogin(), "user1");
        Assert.assertEquals(userList.get(1).getLogin(), "user2");
        Assert.assertEquals(userList.get(2).getLogin(), "user3");

    }

    @Test
    public void getByUUIDUserTest() throws RepositoryAdapterException {
        User a = userService.add(User.builder().firstname("Jan").lastname("User").password("zaq1@WSX").login("user1").userType(UserType.ADMIN).build());

        User get = userService.get(a.getId());

        Assert.assertEquals(get, a);
    }

    @Test
    public void getByLoginUserTest() throws RepositoryAdapterException {
        User a = userService.add(User.builder().firstname("Jan").lastname("User").password("zaq1@WSX").login("user1").userType(UserType.ADMIN).build());

        User get = userService.get(a.getLogin());

        Assert.assertEquals(get, a);
    }
}

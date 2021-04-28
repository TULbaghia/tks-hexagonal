package pl.lodz.p.it.tks.user.soap.user;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pl.lodz.p.it.tks.user.domainmodel.user.User;
import pl.lodz.p.it.tks.user.domainmodel.user.UserType;
import pl.lodz.p.it.tks.user.soap.Util;
import pl.soap.target.UserAPI;
import pl.soap.target.UserService;
import pl.soap.target.RepositoryAdapterException_Exception;
import pl.soap.target.UserSoap;

import javax.xml.ws.BindingProvider;
import javax.xml.ws.Holder;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;


public class UserServiceTests {
    private final String WSDL_URI = "http://localhost:8080/UserSoap/UserAPI?wsdl";

    private UserService userService;
    private User mockUser;

    @BeforeMethod
    public void beforeEach() {
        UserAPI userAPI = new UserAPI(UserAPI.class.getResource("UserAPI.wsdl"));
        userService = userAPI.getUserServicePort();
        Util.authenticateUser((BindingProvider) userService, "TestUser", "zaq1@WSX");
        ((BindingProvider) userService).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, WSDL_URI);

        int randomNum = ThreadLocalRandom.current().nextInt(112312, 888888);
        mockUser = User.builder()
                .firstname("TestName")
                .lastname("TestLastname")
                .login("MockLogin" + randomNum)
                .password("zaq1@WSX")
                .userType(UserType.ADMIN)
                .build();

        Holder<String> constUserId = new Holder<>(null);
        Holder<String> firstname = new Holder<>(mockUser.getFirstname());
        Holder<String> lastname = new Holder<>(mockUser.getLastname());
        Holder<String> login = new Holder<>(mockUser.getLogin());
        Holder<String> password = new Holder<>(mockUser.getPassword());
        Holder<String> userType = new Holder<>("ADMIN");
        Holder<Boolean> isActive = new Holder<>(mockUser.isActive());

        userService.addUser(constUserId, firstname, lastname, login, password, userType, isActive);
        mockUser.setId(UUID.fromString(constUserId.value));
    }

    @Test
    public void addUserTest() {
        int randomNum = ThreadLocalRandom.current().nextInt(112312, 888888);
        Holder<String> id = new Holder<>(null);
        Holder<String> firstname = new Holder<>("TestName");
        Holder<String> lastname = new Holder<>("LastNameTest");
        Holder<String> login = new Holder<>("LoginTest" + randomNum);
        Holder<String> password = new Holder<>("zaq1@WSX");
        Holder<String> userType = new Holder<>("ADMIN");
        Holder<Boolean> isActive = new Holder<>(false);

        List<UserSoap> allUser = userService.getAllUsers();
        int sizeBeforeAdd = allUser.size();

        userService.addUser(id, firstname, lastname, login, password, userType, isActive);

        allUser = userService.getAllUsers();
        Assert.assertEquals(allUser.size(), sizeBeforeAdd + 1);
    }

    @Test
    public void getUserTest() throws RepositoryAdapterException_Exception {
        UserSoap userSoap = userService.getUser(mockUser.getId().toString());

        Assert.assertEquals(userSoap.getId(), mockUser.getId().toString());
        Assert.assertEquals(userSoap.getFirstname(), mockUser.getFirstname());
        Assert.assertEquals(userSoap.getLastname(), mockUser.getLastname());
        Assert.assertEquals(userSoap.getLogin(), mockUser.getLogin());
    }

    @Test
    public void getAllUsersTest() {
        List<UserSoap> allUser = userService.getAllUsers();

        Assert.assertNotEquals(allUser.size(), 0);
    }

    @Test
    public void updateUserTest() throws RepositoryAdapterException_Exception {
        Holder<String> constUserId = new Holder<>((mockUser.getId().toString()));
        Holder<String> firstname = new Holder<>("UpdatedFirstName");
        Holder<String> lastname = new Holder<>("UpdatedLastname");
        Holder<String> login = new Holder<>(mockUser.getLogin());
        Holder<String> password = new Holder<>(mockUser.getPassword());
        Holder<String> userType = new Holder<>("ADMIN");
        Holder<Boolean> isActive = new Holder<>(mockUser.isActive());


        List<UserSoap> allUser = userService.getAllUsers();
        int sizeBeforeAdd = allUser.size();

        userService.updateUser(constUserId, firstname, lastname, login, password, userType, isActive);

        allUser = userService.getAllUsers();
        Assert.assertEquals(allUser.size(), sizeBeforeAdd);

        UserSoap userSoap = userService.getUser(mockUser.getId().toString());

        Assert.assertEquals(userSoap.getId(), mockUser.getId().toString());
        Assert.assertEquals(userSoap.getFirstname(), firstname.value);
        Assert.assertEquals(userSoap.getLastname(), lastname.value);
        Assert.assertEquals(userSoap.getLogin(), login.value);
        Assert.assertEquals(userSoap.getPassword(), password.value);
    }

    @Test
    public void activateUserTest() throws RepositoryAdapterException_Exception {
        Holder<String> constUserId = new Holder<>((mockUser.getId().toString()));
        Holder<String> firstname = new Holder<>(mockUser.getFirstname());
        Holder<String> lastname = new Holder<>(mockUser.getLastname());
        Holder<String> login = new Holder<>(mockUser.getLogin());
        Holder<String> password = new Holder<>(mockUser.getPassword());
        Holder<String> userType = new Holder<>("ADMIN");
        Holder<Boolean> isActive = new Holder<>(false);

        userService.activateUser(constUserId, firstname, lastname, login, password, userType, isActive);

        UserSoap userSoap = userService.getUser(mockUser.getId().toString());

        Assert.assertEquals(userSoap.getId(), mockUser.getId().toString());
        Assert.assertEquals(userSoap.isActive(), isActive.value);
    }
}

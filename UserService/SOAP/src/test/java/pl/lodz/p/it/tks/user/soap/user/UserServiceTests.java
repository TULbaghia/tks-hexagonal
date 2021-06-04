package pl.lodz.p.it.tks.user.soap.user;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.images.builder.ImageFromDockerfile;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import pl.lodz.p.it.tks.user.domainmodel.user.User;
import pl.lodz.p.it.tks.user.domainmodel.user.UserType;
import pl.lodz.p.it.tks.user.soap.Util;
import pl.soap.target.UserAPI;
import pl.soap.target.UserService;
import pl.soap.target.RepositoryAdapterException_Exception;
import pl.soap.target.UserSoap;

import javax.xml.ws.BindingProvider;
import javax.xml.ws.Holder;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@SuppressWarnings("rawtypes")
@Testcontainers
public class UserServiceTests {
    private static String WSDL_URI = "http://localhost:%d/UserServiceApp-1.0-SNAPSHOT/UserAPI?wsdl";

    private UserService userService;
    private User mockUser;

    @Container
    private static GenericContainer serviceOne = new GenericContainer<>(
            new ImageFromDockerfile()
                    .withDockerfileFromBuilder(builder
                            -> builder
                            .from("payara/server-full:5.2020.7-jdk11")
                            .copy("UserServiceApp-1.0-SNAPSHOT.war", "/opt/payara/deployments")
                            .copy("RentServiceApp-1.0-SNAPSHOT.war", "/opt/payara/deployments")
                            .build())
                    .withFileFromPath("UserServiceApp-1.0-SNAPSHOT.war",
                            Path.of("target", "../../UserServiceApp/target/UserServiceApp-1.0-SNAPSHOT.war"))
                    .withFileFromPath("RentServiceApp-1.0-SNAPSHOT.war",
                            Path.of("target", "../../../RentService/RentServiceApp/target/RentServiceApp-1.0-SNAPSHOT.war"))
    ).withExposedPorts(8181, 8080, 4848).waitingFor(Wait.forHttp("/UserServiceApp-1.0-SNAPSHOT/api/start").forPort(8080).forStatusCode(200));

    @BeforeAll
    public static void setup() {
        serviceOne.start();
        WSDL_URI = String.format(WSDL_URI, serviceOne.getMappedPort(8080));
    }

    @BeforeEach
    public void beforeEach() throws RepositoryAdapterException_Exception {
        UserAPI userAPI = new UserAPI(UserAPI.class.getResource("UserAPI.wsdl"));
        userService = userAPI.getUserServicePort();
        Util.authenticateUser((BindingProvider) userService, "TestAdmin", "zaq1@WSX", serviceOne.getMappedPort(8080));
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
    public void addUserTest() throws RepositoryAdapterException_Exception {
        int randomNum = ThreadLocalRandom.current().nextInt(112312, 888888);
        Holder<String> id = new Holder<>(null);
        Holder<String> firstname = new Holder<>("DummyName1306");
        Holder<String> lastname = new Holder<>("LastNameTest");
        Holder<String> login = new Holder<>("LoginTest" + randomNum);
        Holder<String> password = new Holder<>("zaq1@WSX");
        Holder<String> userType = new Holder<>("ADMIN");
        Holder<Boolean> isActive = new Holder<>(false);

        List<UserSoap> allUser = userService.getAllUsers();
        int sizeBeforeAdd = allUser.size();

        userService.addUser(id, firstname, lastname, login, password, userType, isActive);

        allUser = userService.getAllUsers();
        Assertions.assertEquals(allUser.size(), sizeBeforeAdd + 1);
    }

    @Test
    public void getUserTest() throws RepositoryAdapterException_Exception {
        UserSoap userSoap = userService.getUser(mockUser.getId().toString());

        Assertions.assertEquals(userSoap.getId(), mockUser.getId().toString());
        Assertions.assertEquals(userSoap.getFirstname(), mockUser.getFirstname());
        Assertions.assertEquals(userSoap.getLastname(), mockUser.getLastname());
        Assertions.assertEquals(userSoap.getLogin(), mockUser.getLogin());
    }

    @Test
    public void getAllUsersTest() {
        List<UserSoap> allUser = userService.getAllUsers();

        Assertions.assertNotEquals(allUser.size(), 0);
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
        Assertions.assertEquals(allUser.size(), sizeBeforeAdd);

        UserSoap userSoap = userService.getUser(mockUser.getId().toString());

        Assertions.assertEquals(userSoap.getId(), mockUser.getId().toString());
        Assertions.assertEquals(userSoap.getFirstname(), firstname.value);
        Assertions.assertEquals(userSoap.getLastname(), lastname.value);
        Assertions.assertEquals(userSoap.getLogin(), login.value);
        Assertions.assertEquals(userSoap.getPassword(), password.value);
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

        Assertions.assertEquals(userSoap.getId(), mockUser.getId().toString());
        Assertions.assertEquals(userSoap.isActive(), isActive.value);
    }
}

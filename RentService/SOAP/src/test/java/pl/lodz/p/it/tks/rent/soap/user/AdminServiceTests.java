package pl.lodz.p.it.tks.rent.soap.user;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pl.lodz.p.it.tks.rent.domainmodel.user.Admin;
import pl.lodz.p.it.tks.rent.soap.Util;
import pl.soap.target.AdminAPI;
import pl.soap.target.AdminService;
import pl.soap.target.RepositoryAdapterException_Exception;
import pl.soap.target.UserSoap;

import javax.xml.ws.BindingProvider;
import javax.xml.ws.Holder;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;


public class AdminServiceTests {
    private final String WSDL_URI = "http://localhost:8080/SOAP-1.0-SNAPSHOT/AdminAPI?wsdl";

    private AdminService adminService;
    private Admin mockAdmin;

    @BeforeMethod
    public void beforeEach() {
        AdminAPI adminAPI = new AdminAPI(AdminAPI.class.getResource("AdminAPI.wsdl"));
        adminService = adminAPI.getAdminServicePort();
        Util.authenticateUser((BindingProvider) adminService, "TestAdmin", "zaq1@WSX");
        ((BindingProvider) adminService).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, WSDL_URI);

        int randomNum = ThreadLocalRandom.current().nextInt(112312, 888888);
        mockAdmin = Admin.builder()
                .firstname("TestName")
                .lastname("TestLastname")
                .login("MockLogin" + randomNum)
                .password("zaq1@WSX")
                .build();

        Holder<String> constUserId = new Holder<>(null);
        Holder<String> firstname = new Holder<>(mockAdmin.getFirstname());
        Holder<String> lastname = new Holder<>(mockAdmin.getLastname());
        Holder<String> login = new Holder<>(mockAdmin.getLogin());
        Holder<String> password = new Holder<>(mockAdmin.getPassword());
        Holder<Boolean> isActive = new Holder<>(mockAdmin.isActive());

        adminService.addAdmin(constUserId, firstname, lastname, login, password, isActive);
        mockAdmin.setId(UUID.fromString(constUserId.value));
    }

    @Test
    public void addAdminTest() {
        int randomNum = ThreadLocalRandom.current().nextInt(112312, 888888);
        Holder<String> id = new Holder<>(null);
        Holder<String> firstname = new Holder<>("TestName");
        Holder<String> lastname = new Holder<>("LastNameTest");
        Holder<String> login = new Holder<>("LoginTest" + randomNum);
        Holder<String> password = new Holder<>("zaq1@WSX");
        Holder<Boolean> isActive = new Holder<>(false);

        List<UserSoap> allAdmin = adminService.getAllAdmins();
        int sizeBeforeAdd = allAdmin.size();

        adminService.addAdmin(id, firstname, lastname, login, password, isActive);

        allAdmin = adminService.getAllAdmins();
        Assert.assertEquals(allAdmin.size(), sizeBeforeAdd + 1);
    }

    @Test
    public void getAdminTest() throws RepositoryAdapterException_Exception {
        UserSoap userSoap = adminService.getAdmin(mockAdmin.getId().toString());

        Assert.assertEquals(userSoap.getId(), mockAdmin.getId().toString());
        Assert.assertEquals(userSoap.getFirstname(), mockAdmin.getFirstname());
        Assert.assertEquals(userSoap.getLastname(), mockAdmin.getLastname());
        Assert.assertEquals(userSoap.getLogin(), mockAdmin.getLogin());
    }

    @Test
    public void getAllAdminsTest() {
        List<UserSoap> allAdmin = adminService.getAllAdmins();

        Assert.assertNotEquals(allAdmin.size(), 0);
    }

    @Test
    public void updateAdminTest() throws RepositoryAdapterException_Exception {
        Holder<String> constUserId = new Holder<>((mockAdmin.getId().toString()));
        Holder<String> firstname = new Holder<>("UpdatedFirstName");
        Holder<String> lastname = new Holder<>("UpdatedLastname");
        Holder<String> login = new Holder<>(mockAdmin.getLogin());
        Holder<String> password = new Holder<>(mockAdmin.getPassword());
        Holder<Boolean> isActive = new Holder<>(mockAdmin.isActive());


        List<UserSoap> allAdmin = adminService.getAllAdmins();
        int sizeBeforeAdd = allAdmin.size();

        adminService.updateAdmin(constUserId, firstname, lastname, login, password, isActive);

        allAdmin = adminService.getAllAdmins();
        Assert.assertEquals(allAdmin.size(), sizeBeforeAdd);

        UserSoap userSoap = adminService.getAdmin(mockAdmin.getId().toString());

        Assert.assertEquals(userSoap.getId(), mockAdmin.getId().toString());
        Assert.assertEquals(userSoap.getFirstname(), firstname.value);
        Assert.assertEquals(userSoap.getLastname(), lastname.value);
        Assert.assertEquals(userSoap.getLogin(), login.value);
        Assert.assertEquals(userSoap.getPassword(), password.value);
    }

    @Test
    public void activateAdminTest() throws RepositoryAdapterException_Exception {
        Holder<String> constUserId = new Holder<>((mockAdmin.getId().toString()));
        Holder<String> firstname = new Holder<>(mockAdmin.getFirstname());
        Holder<String> lastname = new Holder<>(mockAdmin.getLastname());
        Holder<String> login = new Holder<>(mockAdmin.getLogin());
        Holder<String> password = new Holder<>(mockAdmin.getPassword());
        Holder<Boolean> isActive = new Holder<>(false);

        adminService.activateAdmin(constUserId, firstname, lastname, login, password, isActive);

        UserSoap userSoap = adminService.getAdmin(mockAdmin.getId().toString());

        Assert.assertEquals(userSoap.getId(), mockAdmin.getId().toString());
        Assert.assertEquals(userSoap.isActive(), isActive.value);
    }
}

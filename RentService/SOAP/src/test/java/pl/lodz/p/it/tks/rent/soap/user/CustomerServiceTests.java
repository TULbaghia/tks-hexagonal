package pl.lodz.p.it.tks.rent.soap.user;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pl.lodz.p.it.tks.rent.domainmodel.user.Customer;
import pl.lodz.p.it.tks.rent.soap.Util;
import pl.soap.target.CustomerAPI;
import pl.soap.target.CustomerService;
import pl.soap.target.RepositoryAdapterException_Exception;
import pl.soap.target.UserSoap;

import javax.xml.ws.BindingProvider;
import javax.xml.ws.Holder;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;


public class CustomerServiceTests {
    private final String WSDL_URI = "http://localhost:8080/SOAP-1.0-SNAPSHOT/CustomerAPI?wsdl";

    private CustomerService customerService;
    private Customer mockCustomer;

    @BeforeMethod
    public void beforeEach() {
        CustomerAPI customerAPI = new CustomerAPI(CustomerAPI.class.getResource("CustomerAPI.wsdl"));
        customerService = customerAPI.getCustomerServicePort();
        Util.authenticateUser((BindingProvider) customerService, "TestCustomer", "zaq1@WSX");
        ((BindingProvider) customerService).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, WSDL_URI);

        int randomNum = ThreadLocalRandom.current().nextInt(112312, 888888);
        mockCustomer = Customer.builder()
                .firstname("TestName")
                .lastname("TestLastname")
                .login("MockLogin" + randomNum)
                .password("zaq1@WSX")
                .build();

        Holder<String> constUserId = new Holder<>(null);
        Holder<String> firstname = new Holder<>(mockCustomer.getFirstname());
        Holder<String> lastname = new Holder<>(mockCustomer.getLastname());
        Holder<String> login = new Holder<>(mockCustomer.getLogin());
        Holder<String> password = new Holder<>(mockCustomer.getPassword());
        Holder<Boolean> isActive = new Holder<>(mockCustomer.isActive());

        customerService.addCustomer(constUserId, firstname, lastname, login, password, isActive);
        mockCustomer.setId(UUID.fromString(constUserId.value));
    }

    @Test
    public void addCustomerTest() {
        int randomNum = ThreadLocalRandom.current().nextInt(112312, 888888);
        Holder<String> id = new Holder<>(null);
        Holder<String> firstname = new Holder<>("TestName");
        Holder<String> lastname = new Holder<>("LastNameTest");
        Holder<String> login = new Holder<>("LoginTest"+ randomNum);
        Holder<String> password = new Holder<>("zaq1@WSX");
        Holder<Boolean> isActive = new Holder<>(false);

        List<UserSoap> allCustomer = customerService.getAllCustomers();
        int sizeBeforeAdd = allCustomer.size();

        customerService.addCustomer(id, firstname, lastname, login, password, isActive);

        allCustomer = customerService.getAllCustomers();
        Assert.assertEquals(allCustomer.size(), sizeBeforeAdd + 1);
    }

    @Test
    public void getCustomerTest() throws RepositoryAdapterException_Exception {
        UserSoap userSoap = customerService.getCustomer(mockCustomer.getId().toString());

        Assert.assertEquals(userSoap.getId(), mockCustomer.getId().toString());
        Assert.assertEquals(userSoap.getFirstname(), mockCustomer.getFirstname());
        Assert.assertEquals(userSoap.getLastname(), mockCustomer.getLastname());
        Assert.assertEquals(userSoap.getLogin(), mockCustomer.getLogin());
    }

    @Test
    public void getAllCustomersTest() {
        List<UserSoap> allCustomer = customerService.getAllCustomers();

        Assert.assertNotEquals(allCustomer.size(), 0);
    }

    @Test
    public void updateCustomerTest() throws RepositoryAdapterException_Exception {
        Holder<String> constUserId = new Holder<>((mockCustomer.getId().toString()));
        Holder<String> firstname = new Holder<>("UpdatedFirstName");
        Holder<String> lastname = new Holder<>("UpdatedLastname");
        Holder<String> login = new Holder<>(mockCustomer.getLogin());
        Holder<String> password = new Holder<>(mockCustomer.getPassword());
        Holder<Boolean> isActive = new Holder<>(mockCustomer.isActive());


        List<UserSoap> allCustomer = customerService.getAllCustomers();
        int sizeBeforeAdd = allCustomer.size();

        customerService.updateCustomer(constUserId, firstname, lastname, login, password, isActive);

        allCustomer = customerService.getAllCustomers();
        Assert.assertEquals(allCustomer.size(), sizeBeforeAdd);

        UserSoap userSoap = customerService.getCustomer(mockCustomer.getId().toString());

        Assert.assertEquals(userSoap.getId(), mockCustomer.getId().toString());
        Assert.assertEquals(userSoap.getFirstname(), firstname.value);
        Assert.assertEquals(userSoap.getLastname(), lastname.value);
        Assert.assertEquals(userSoap.getLogin(), login.value);
        Assert.assertEquals(userSoap.getPassword(), password.value);
    }

    @Test
    public void activateCustomerTest() throws RepositoryAdapterException_Exception {
        Holder<String> constUserId = new Holder<>((mockCustomer.getId().toString()));
        Holder<String> firstname = new Holder<>(mockCustomer.getFirstname());
        Holder<String> lastname = new Holder<>(mockCustomer.getLastname());
        Holder<String> login = new Holder<>(mockCustomer.getLogin());
        Holder<String> password = new Holder<>(mockCustomer.getPassword());
        Holder<Boolean> isActive = new Holder<>(false);

        customerService.activateCustomer(constUserId, firstname, lastname, login, password, isActive);

        UserSoap userSoap = customerService.getCustomer(mockCustomer.getId().toString());

        Assert.assertEquals(userSoap.getId(), mockCustomer.getId().toString());
        Assert.assertEquals(userSoap.isActive(), isActive.value);
    }
}

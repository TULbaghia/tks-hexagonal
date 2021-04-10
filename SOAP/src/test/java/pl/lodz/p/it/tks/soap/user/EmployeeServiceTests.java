package pl.lodz.p.it.tks.soap.user;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pl.lodz.p.it.tks.domainmodel.user.Employee;
import pl.lodz.p.it.tks.soap.Util;
import pl.soap.target.EmployeeAPI;
import pl.soap.target.EmployeeService;
import pl.soap.target.RepositoryAdapterException_Exception;
import pl.soap.target.UserSoap;

import javax.xml.ws.BindingProvider;
import javax.xml.ws.Holder;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;


public class EmployeeServiceTests {
    private final String WSDL_URI = "http://localhost:8080/SOAP-1.0-SNAPSHOT/EmployeeAPI?wsdl";

    private EmployeeService employeeService;
    private Employee mockEmployee;

    @BeforeMethod
    public void beforeEach() {
        EmployeeAPI employeeAPI = new EmployeeAPI(EmployeeAPI.class.getResource("EmployeeAPI.wsdl"));
        employeeService = employeeAPI.getEmployeeServicePort();
        Util.authenticateUser((BindingProvider) employeeService, "TestEmployee", "zaq1@WSX");
        ((BindingProvider) employeeService).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, WSDL_URI);

        int randomNum = ThreadLocalRandom.current().nextInt(112312, 888888);
        mockEmployee = Employee.builder()
                .firstname("TestName")
                .lastname("TestLastname")
                .login("MockLogin" + randomNum)
                .password("zaq1@WSX")
                .build();

        Holder<String> constUserId = new Holder<>(null);
        Holder<String> firstname = new Holder<>(mockEmployee.getFirstname());
        Holder<String> lastname = new Holder<>(mockEmployee.getLastname());
        Holder<String> login = new Holder<>(mockEmployee.getLogin());
        Holder<String> password = new Holder<>(mockEmployee.getPassword());
        Holder<Boolean> isActive = new Holder<>(mockEmployee.isActive());

        employeeService.addEmployee(constUserId, firstname, lastname, login, password, isActive);
        mockEmployee.setId(UUID.fromString(constUserId.value));
    }

    @Test
    public void addEmployeeTest() {
        int randomNum = ThreadLocalRandom.current().nextInt(112312, 888888);
        Holder<String> id = new Holder<>(null);
        Holder<String> firstname = new Holder<>("TestName");
        Holder<String> lastname = new Holder<>("LastNameTest");
        Holder<String> login = new Holder<>("LoginTest" + randomNum);
        Holder<String> password = new Holder<>("zaq1@WSX");
        Holder<Boolean> isActive = new Holder<>(false);

        List<UserSoap> allEmployee = employeeService.getAllEmployees();
        int sizeBeforeAdd = allEmployee.size();

        employeeService.addEmployee(id, firstname, lastname, login, password, isActive);

        allEmployee = employeeService.getAllEmployees();
        Assert.assertEquals(allEmployee.size(), sizeBeforeAdd + 1);
    }

    @Test
    public void getEmployeeTest() throws RepositoryAdapterException_Exception {
        UserSoap userSoap = employeeService.getEmployee(mockEmployee.getId().toString());

        Assert.assertEquals(userSoap.getId(), mockEmployee.getId().toString());
        Assert.assertEquals(userSoap.getFirstname(), mockEmployee.getFirstname());
        Assert.assertEquals(userSoap.getLastname(), mockEmployee.getLastname());
        Assert.assertEquals(userSoap.getLogin(), mockEmployee.getLogin());
    }

    @Test
    public void getAllEmployeesTest() {
        List<UserSoap> allEmployee = employeeService.getAllEmployees();

        Assert.assertNotEquals(allEmployee.size(), 0);
    }

    @Test
    public void updateEmployeeTest() throws RepositoryAdapterException_Exception {
        Holder<String> constUserId = new Holder<>((mockEmployee.getId().toString()));
        Holder<String> firstname = new Holder<>("UpdatedFirstName");
        Holder<String> lastname = new Holder<>("UpdatedLastname");
        Holder<String> login = new Holder<>(mockEmployee.getLogin());
        Holder<String> password = new Holder<>(mockEmployee.getPassword());
        Holder<Boolean> isActive = new Holder<>(mockEmployee.isActive());


        List<UserSoap> allEmployee = employeeService.getAllEmployees();
        int sizeBeforeAdd = allEmployee.size();

        employeeService.updateEmployee(constUserId, firstname, lastname, login, password, isActive);

        allEmployee = employeeService.getAllEmployees();
        Assert.assertEquals(allEmployee.size(), sizeBeforeAdd);

        UserSoap userSoap = employeeService.getEmployee(mockEmployee.getId().toString());

        Assert.assertEquals(userSoap.getId(), mockEmployee.getId().toString());
        Assert.assertEquals(userSoap.getFirstname(), firstname.value);
        Assert.assertEquals(userSoap.getLastname(), lastname.value);
        Assert.assertEquals(userSoap.getLogin(), login.value);
        Assert.assertEquals(userSoap.getPassword(), password.value);
    }
}

package pl.lodz.p.it.tks.rent.soap.resources;

import org.javatuples.Pair;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pl.lodz.p.it.tks.rent.soap.Util;
import pl.soap.target.*;
import pl.soap.target.EconomyCarService;
import pl.soap.target.RentService;

import javax.xml.ws.BindingProvider;
import javax.xml.ws.Holder;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class RentServiceTests {
    private final String RENTAPI_WSDL_URI = "http://localhost:8080/SOAP-1.0-SNAPSHOT/RentAPI?wsdl";
    private final String ECONOMYCARAPI_WSDL_URI = "http://localhost:8080/SOAP-1.0-SNAPSHOT/EconomyCarAPI?wsdl";
    private final String CUSTOMERAPI_WSDL_URI = "http://localhost:8080/SOAP-1.0-SNAPSHOT/CustomerAPI?wsdl";

    private RentService rentService;
    private EconomyCarService economyCarService;
    private CustomerService customerService;

    @BeforeMethod
    public void beforeEach() {
        RentAPI rentAPI = new RentAPI(RentAPI.class.getResource("RentAPI.wsdl"));
        EconomyCarAPI economyCarAPI = new EconomyCarAPI(EconomyCarAPI.class.getResource("EconomyCarAPI.wsdl"));
        CustomerAPI customerAPI = new CustomerAPI(CustomerAPI.class.getResource("CustomerAPI.wsdl"));

        rentService = rentAPI.getRentServicePort();
        economyCarService = economyCarAPI.getEconomyCarServicePort();
        customerService = customerAPI.getCustomerServicePort();

        Util.authenticateUser((BindingProvider) rentService, "TestEmployee", "zaq1@WSX");
        Util.authenticateUser((BindingProvider) economyCarService, "TestEmployee", "zaq1@WSX");
        Util.authenticateUser((BindingProvider) customerService, "TestEmployee", "zaq1@WSX");

        ((BindingProvider) rentService).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, RENTAPI_WSDL_URI);
        ((BindingProvider) economyCarService).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, ECONOMYCARAPI_WSDL_URI);
        ((BindingProvider) customerService).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, CUSTOMERAPI_WSDL_URI);
    }

    private Pair<String, String> createResources() {
        int randomNum = ThreadLocalRandom.current().nextInt(112312, 888888);

        Holder<String> carId = new Holder<>("");
        Holder<Double> engineCapacity = new Holder<>(1.5);
        Holder<String> vin = new Holder<>("12345" + randomNum);
        Holder<Integer> doorNumber = new Holder<>(randomNum);
        Holder<String> brand = new Holder<>("BMWtest");
        Holder<Double> basePricePerDay = new Holder<>(1500d);
        Holder<String> driverEquipment = new Holder<>("TestEq");
        economyCarService.addEconomyCar(carId, engineCapacity, vin, doorNumber, brand, basePricePerDay, driverEquipment);

        Holder<String> customerId = new Holder<>("");
        Holder<String> firstname = new Holder<>("TestName");
        Holder<String> lastname = new Holder<>("LastNameTest");
        Holder<String> login = new Holder<>("LoginTest" + randomNum);
        Holder<String> password = new Holder<>("zaq1@WSX");
        Holder<Boolean> isActive = new Holder<>(false);
        customerService.addCustomer(customerId, firstname, lastname, login, password, isActive);

        return new Pair<>(carId.value, customerId.value);
    }

    @Test
    public void addRentTest() throws RepositoryAdapterException_Exception {
        Holder<String> constRentId = new Holder<>(null);
        Pair<String, String> resources = createResources();
        Holder<String> carId = new Holder<>(resources.getValue0());
        Holder<String> customerId = new Holder<>(resources.getValue1());
        Holder<String> rentStartDate = new Holder<>(LocalDateTime.now().toString());
        Holder<String> rentEndDate = new Holder<>(null);
        Holder<Double> price = new Holder<>(0.0);

        List<RentSoap> allRents = rentService.getAllRents();
        int sizeBeforeAdd = allRents.size();

        rentService.addRent(constRentId, carId, customerId, rentStartDate, rentEndDate, price);

        allRents = rentService.getAllRents();
        Assert.assertEquals(allRents.size(), sizeBeforeAdd + 1);
    }

    @Test
    public void getRentTest() throws RepositoryAdapterException_Exception {
        Holder<String> constRentId = new Holder<>(null);
        Pair<String, String> resources = createResources();
        Holder<String> carId = new Holder<>(resources.getValue0());
        Holder<String> customerId = new Holder<>(resources.getValue1());
        Holder<String> rentStartDate = new Holder<>(LocalDateTime.now().toString());
        Holder<String> rentEndDate = new Holder<>(null);
        Holder<Double> price = new Holder<>(0.0);

        rentService.addRent(constRentId, carId, customerId, rentStartDate, rentEndDate, price);

        RentSoap rentSoap = rentService.getRent(constRentId.value);

        Assert.assertEquals(rentSoap.getId(), constRentId.value);
        Assert.assertEquals(rentSoap.getCarId(), carId.value);
        Assert.assertEquals(rentSoap.getCustomerId(), customerId.value);
        Assert.assertEquals(rentSoap.getRentStartDate(), rentStartDate.value);
        Assert.assertEquals(rentSoap.getRentEndDate(), rentEndDate.value);
        Assert.assertEquals(rentSoap.getPrice(), (double) price.value);
    }

    @Test
    public void getAllRentsTest() throws RepositoryAdapterException_Exception {
        List<RentSoap> allRents = rentService.getAllRents();

        Assert.assertNotEquals(allRents.size(), 0);
    }

    @Test
    public void updateRentTest() throws RepositoryAdapterException_Exception {
        Holder<String> constRentId = new Holder<>(null);
        Pair<String, String> resources = createResources();
        Holder<String> carId = new Holder<>(resources.getValue0());
        Holder<String> customerId = new Holder<>(resources.getValue1());
        Holder<String> rentStartDate = new Holder<>(LocalDateTime.now().toString());
        Holder<String> rentEndDate = new Holder<>(null);
        Holder<Double> price = new Holder<>(0.0);

        List<RentSoap> allRents = rentService.getAllRents();
        int sizeBeforeAdd = allRents.size();

        rentService.addRent(constRentId, carId, customerId, rentStartDate, rentEndDate, price);

        allRents = rentService.getAllRents();
        Assert.assertEquals(allRents.size(), sizeBeforeAdd + 1);

        resources = createResources();
        carId = new Holder<>(resources.getValue0());
        rentService.updateRent(constRentId, carId, customerId, rentStartDate, rentEndDate, price);
        RentSoap rentSoap = rentService.getRent(constRentId.value);

        Assert.assertEquals(rentSoap.getCarId(), carId.value);
    }

    @Test
    public void deleteRentTest() throws RepositoryAdapterException_Exception {
        Holder<String> constRentId = new Holder<>(null);
        Pair<String, String> resources = createResources();
        Holder<String> carId = new Holder<>(resources.getValue0());
        Holder<String> customerId = new Holder<>(resources.getValue1());
        Holder<String> rentStartDate = new Holder<>(LocalDateTime.now().toString());
        Holder<String> rentEndDate = new Holder<>(null);
        Holder<Double> price = new Holder<>(0.0);

        List<RentSoap> allRents = rentService.getAllRents();
        int sizeBeforeAdd = allRents.size();

        rentService.addRent(constRentId, carId, customerId, rentStartDate, rentEndDate, price);

        allRents = rentService.getAllRents();
        Assert.assertEquals(allRents.size(), sizeBeforeAdd + 1);

        rentService.deleteRent(constRentId.value);

        allRents = rentService.getAllRents();
        Assert.assertEquals(allRents.size(), sizeBeforeAdd);
    }

    @Test
    public void endRentTest() throws RepositoryAdapterException_Exception {
        Holder<String> constRentId = new Holder<>(null);
        Pair<String, String> resources = createResources();
        Holder<String> carId = new Holder<>(resources.getValue0());
        Holder<String> customerId = new Holder<>(resources.getValue1());
        Holder<String> rentStartDate = new Holder<>(LocalDateTime.now().toString());
        Holder<String> rentEndDate = new Holder<>(null);
        Holder<Double> price = new Holder<>(0.0);

        rentService.addRent(constRentId, carId, customerId, rentStartDate, rentEndDate, price);

        rentService.endRent(constRentId, carId, customerId, rentStartDate, rentEndDate, price);

        RentSoap rentSoap = rentService.getRent(constRentId.value);

        Assert.assertNotNull(rentSoap.getRentEndDate());
        Assert.assertNotEquals(rentSoap.getPrice(), 0);
    }
}

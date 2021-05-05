package pl.lodz.p.it.tks.rent.soap.resources;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pl.lodz.p.it.tks.rent.domainmodel.resources.EconomyCar;
import pl.lodz.p.it.tks.rent.soap.Util;
import pl.soap.target.EconomyCarAPI;
import pl.soap.target.EconomyCarService;
import pl.soap.target.EconomyCarSoap;

import javax.xml.ws.BindingProvider;
import javax.xml.ws.Holder;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class EconomyCarServiceTests {
    private final String WSDL_URI = "http://localhost:8080/RentSoap/EconomyCarAPI?wsdl";

    private EconomyCarService economyCarService;
    private EconomyCar mockEconomyCar;

    @BeforeMethod
    public void beforeEach() {
        EconomyCarAPI economyCarAPI = new EconomyCarAPI(EconomyCarAPI.class.getResource("EconomyCarAPI.wsdl"));
        economyCarService = economyCarAPI.getEconomyCarServicePort();

        Util.authenticateUser((BindingProvider) economyCarService, "TestEmployee", "zaq1@WSX");

        ((BindingProvider) economyCarService).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, WSDL_URI);

        int randomNum = ThreadLocalRandom.current().nextInt(112312, 888888);
        mockEconomyCar = EconomyCar.builder()
                .engineCapacity(1.5)
                .vin("12345" + randomNum)
                .doorNumber(5)
                .brand("BMW")
                .basePricePerDay(1000d)
                .driverEquipment("TestEquipment")
                .build();

        Holder<String> constCarId = new Holder<>(null);
        Holder<Double> engineCapacity = new Holder<>(mockEconomyCar.getEngineCapacity());
        Holder<String> vin = new Holder<>(mockEconomyCar.getVin());
        Holder<Integer> doorNumber = new Holder<>(mockEconomyCar.getDoorNumber());
        Holder<String> brand = new Holder<>(mockEconomyCar.getBrand());
        Holder<Double> basePricePerDay = new Holder<>(mockEconomyCar.getBasePricePerDay());
        Holder<String> driverEquipment = new Holder<>(mockEconomyCar.getDriverEquipment());

        economyCarService.addEconomyCar(constCarId, engineCapacity, vin, doorNumber, brand, basePricePerDay, driverEquipment);
        mockEconomyCar.setId(UUID.fromString(constCarId.value));
    }

    @Test
    public void addEconomyCarTest() {
        int randomNum = ThreadLocalRandom.current().nextInt(112312, 888888);
        Holder<String> constCarId = new Holder<>(null);
        Holder<Double> engineCapacity = new Holder<>(1.5);
        Holder<String> vin = new Holder<>("12345" + randomNum);
        Holder<Integer> doorNumber = new Holder<>(mockEconomyCar.getDoorNumber());
        Holder<String> brand = new Holder<>("BMWtest");
        Holder<Double> basePricePerDay = new Holder<>(1500d);
        Holder<String> driverEquipment = new Holder<>("TestEq");

        List<EconomyCarSoap> allCars = economyCarService.getAllEconomyCars();
        int sizeBeforeAdd = allCars.size();

        economyCarService.addEconomyCar(constCarId, engineCapacity, vin, doorNumber, brand, basePricePerDay, driverEquipment);

        allCars = economyCarService.getAllEconomyCars();
        Assert.assertEquals(allCars.size(), sizeBeforeAdd + 1);
    }

    @Test
    public void getEconomyCarTest() {
        EconomyCarSoap economyCarSoap = economyCarService.getEconomyCar(mockEconomyCar.getId().toString());

        Assert.assertEquals(economyCarSoap.getId(), mockEconomyCar.getId().toString());
        Assert.assertEquals(economyCarSoap.getEngineCapacity(), mockEconomyCar.getEngineCapacity());
        Assert.assertEquals(economyCarSoap.getVin(), mockEconomyCar.getVin());
        Assert.assertEquals(economyCarSoap.getDoorNumber(), mockEconomyCar.getDoorNumber());
        Assert.assertEquals(economyCarSoap.getBrand(), mockEconomyCar.getBrand());
        Assert.assertEquals(economyCarSoap.getBasePricePerDay(), mockEconomyCar.getBasePricePerDay());
        Assert.assertEquals(economyCarSoap.getDriverEquipment(), mockEconomyCar.getDriverEquipment());
    }

    @Test
    public void getAllEconomyCarsTest() {
        List<EconomyCarSoap> allCars = economyCarService.getAllEconomyCars();

        Assert.assertNotEquals(allCars.size(), 0);
    }

    @Test
    public void updateEconomyCarTest() {
        Holder<String> constCarId = new Holder<>(mockEconomyCar.getId().toString());
        Holder<Double> engineCapacity = new Holder<>(mockEconomyCar.getEngineCapacity());
        Holder<String> vin = new Holder<>(mockEconomyCar.getVin());
        Holder<Integer> doorNumber = new Holder<>(1337);
        Holder<String> brand = new Holder<>("UpdatedBrand");
        Holder<Double> basePricePerDay = new Holder<>(mockEconomyCar.getBasePricePerDay());
        Holder<String> driverEquipment = new Holder<>(mockEconomyCar.getDriverEquipment());

        List<EconomyCarSoap> allCars = economyCarService.getAllEconomyCars();
        int sizeBeforeAdd = allCars.size();

        economyCarService.updateEconomyCar(constCarId, engineCapacity, vin, doorNumber, brand, basePricePerDay, driverEquipment);

        allCars = economyCarService.getAllEconomyCars();
        Assert.assertEquals(allCars.size(), sizeBeforeAdd);


        EconomyCarSoap economyCarSoap = economyCarService.getEconomyCar(mockEconomyCar.getId().toString());

        Assert.assertEquals(economyCarSoap.getId(), mockEconomyCar.getId().toString());
        Assert.assertEquals(economyCarSoap.getEngineCapacity(), mockEconomyCar.getEngineCapacity());
        Assert.assertEquals(economyCarSoap.getVin(), mockEconomyCar.getVin());
        Assert.assertEquals(economyCarSoap.getDoorNumber(), Integer.toUnsignedLong(doorNumber.value));
        Assert.assertEquals(economyCarSoap.getBrand(), brand.value);
        Assert.assertEquals(economyCarSoap.getBasePricePerDay(), mockEconomyCar.getBasePricePerDay());
        Assert.assertEquals(economyCarSoap.getDriverEquipment(), mockEconomyCar.getDriverEquipment());
    }

    @Test
    public void deleteEconomyCarTest() {
        List<EconomyCarSoap> allCars = economyCarService.getAllEconomyCars();
        int sizeBeforeAdd = allCars.size();

        economyCarService.deleteEconomyCar(mockEconomyCar.getId().toString());

        allCars = economyCarService.getAllEconomyCars();
        Assert.assertEquals(allCars.size(), sizeBeforeAdd - 1);
    }
}

package pl.lodz.p.it.tks.rent.soap.resources;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.lodz.p.it.tks.rent.domainmodel.resources.EconomyCar;
import pl.lodz.p.it.tks.rent.soap.AbstractContainer;
import pl.lodz.p.it.tks.rent.soap.Util;
import pl.soap.target.EconomyCarAPI;
import pl.soap.target.EconomyCarService;
import pl.soap.target.EconomyCarSoap;

import javax.xml.ws.BindingProvider;
import javax.xml.ws.Holder;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class EconomyCarServiceTests extends AbstractContainer {
    private static String WSDL_URI = "http://localhost:%d/RentServiceApp-1.0-SNAPSHOT/EconomyCarAPI?wsdl";

    private static EconomyCarService economyCarService;
    private static EconomyCar mockEconomyCar;

    @BeforeAll
    public static void setup() {
        getService();
        WSDL_URI = String.format(WSDL_URI, serviceOne.getMappedPort(8080));
    }

    @BeforeEach
    public void beforeEach() {
        EconomyCarAPI economyCarAPI = new EconomyCarAPI(EconomyCarAPI.class.getResource("EconomyCarAPI.wsdl"));
        economyCarService = economyCarAPI.getEconomyCarServicePort();

        Util.authenticateUser((BindingProvider) economyCarService, "TestEmployee", "zaq1@WSX",
                serviceOne.getMappedPort(8080));

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
        Assertions.assertEquals(allCars.size(), sizeBeforeAdd + 1);
    }

    @Test
    public void getEconomyCarTest() {
        EconomyCarSoap economyCarSoap = economyCarService.getEconomyCar(mockEconomyCar.getId().toString());

        Assertions.assertEquals(economyCarSoap.getId(), mockEconomyCar.getId().toString());
        Assertions.assertEquals(economyCarSoap.getEngineCapacity(), mockEconomyCar.getEngineCapacity());
        Assertions.assertEquals(economyCarSoap.getVin(), mockEconomyCar.getVin());
        Assertions.assertEquals(economyCarSoap.getDoorNumber(), mockEconomyCar.getDoorNumber());
        Assertions.assertEquals(economyCarSoap.getBrand(), mockEconomyCar.getBrand());
        Assertions.assertEquals(economyCarSoap.getBasePricePerDay(), mockEconomyCar.getBasePricePerDay());
        Assertions.assertEquals(economyCarSoap.getDriverEquipment(), mockEconomyCar.getDriverEquipment());
    }

    @Test
    public void getAllEconomyCarsTest() {
        List<EconomyCarSoap> allCars = economyCarService.getAllEconomyCars();

        Assertions.assertNotEquals(allCars.size(), 0);
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
        Assertions.assertEquals(allCars.size(), sizeBeforeAdd);


        EconomyCarSoap economyCarSoap = economyCarService.getEconomyCar(mockEconomyCar.getId().toString());

        Assertions.assertEquals(economyCarSoap.getId(), mockEconomyCar.getId().toString());
        Assertions.assertEquals(economyCarSoap.getEngineCapacity(), mockEconomyCar.getEngineCapacity());
        Assertions.assertEquals(economyCarSoap.getVin(), mockEconomyCar.getVin());
        Assertions.assertEquals(economyCarSoap.getDoorNumber(), Integer.toUnsignedLong(doorNumber.value));
        Assertions.assertEquals(economyCarSoap.getBrand(), brand.value);
        Assertions.assertEquals(economyCarSoap.getBasePricePerDay(), mockEconomyCar.getBasePricePerDay());
        Assertions.assertEquals(economyCarSoap.getDriverEquipment(), mockEconomyCar.getDriverEquipment());
    }

    @Test
    public void deleteEconomyCarTest() {
        List<EconomyCarSoap> allCars = economyCarService.getAllEconomyCars();
        int sizeBeforeAdd = allCars.size();

        economyCarService.deleteEconomyCar(mockEconomyCar.getId().toString());

        allCars = economyCarService.getAllEconomyCars();
        Assertions.assertEquals(allCars.size(), sizeBeforeAdd - 1);
    }
}

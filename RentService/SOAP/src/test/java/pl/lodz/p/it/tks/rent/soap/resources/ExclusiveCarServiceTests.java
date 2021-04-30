package pl.lodz.p.it.tks.rent.soap.resources;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pl.lodz.p.it.tks.rent.domainmodel.resources.ExclusiveCar;
import pl.lodz.p.it.tks.rent.soap.Util;
import pl.soap.target.ExclusiveCarAPI;
import pl.soap.target.ExclusiveCarService;
import pl.soap.target.ExclusiveCarSoap;

import javax.xml.ws.BindingProvider;
import javax.xml.ws.Holder;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class ExclusiveCarServiceTests {
    private final String WSDL_URI = "http://localhost:8080/RentSoap/ExclusiveCarAPI?wsdl";

    private ExclusiveCarService exclusiveCarService;
    private ExclusiveCar mockExclusiveCar;

    @BeforeMethod
    public void beforeEach() {
        ExclusiveCarAPI exclusiveCarAPI = new ExclusiveCarAPI(ExclusiveCarAPI.class.getResource("ExclusiveCarAPI.wsdl"));
        exclusiveCarService = exclusiveCarAPI.getExclusiveCarServicePort();

        Util.authenticateUser((BindingProvider) exclusiveCarService, "TestEmployee", "zaq1@WSX");

        ((BindingProvider) exclusiveCarService).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, WSDL_URI);

        int randomNum = ThreadLocalRandom.current().nextInt(112312, 888888);
        mockExclusiveCar = ExclusiveCar.builder()
                .engineCapacity(1.5)
                .vin("12345" + randomNum)
                .doorNumber(5)
                .brand("BMW")
                .basePricePerDay(1000d)
                .driverEquipment("TestEquipment")
                .boardPcName("boardPc")
                .build();

        Holder<String> constCarId = new Holder<>(null);
        Holder<Double> engineCapacity = new Holder<>(mockExclusiveCar.getEngineCapacity());
        Holder<String> vin = new Holder<>(mockExclusiveCar.getVin());
        Holder<Integer> doorNumber = new Holder<>(mockExclusiveCar.getDoorNumber());
        Holder<String> brand = new Holder<>(mockExclusiveCar.getBrand());
        Holder<Double> basePricePerDay = new Holder<>(mockExclusiveCar.getBasePricePerDay());
        Holder<String> driverEquipment = new Holder<>(mockExclusiveCar.getDriverEquipment());
        Holder<String> boardPcName = new Holder<>("boardPc");

        exclusiveCarService.addExclusiveCar(constCarId, engineCapacity, vin, doorNumber, brand, basePricePerDay, driverEquipment, boardPcName);
        mockExclusiveCar.setId(UUID.fromString(constCarId.value));
    }

    @Test
    public void addExclusiveCarTest() {
        int randomNum = ThreadLocalRandom.current().nextInt(112312, 888888);
        Holder<String> constCarId = new Holder<>(null);
        Holder<Double> engineCapacity = new Holder<>(1.5);
        Holder<String> vin = new Holder<>("12345" + randomNum);
        Holder<Integer> doorNumber = new Holder<>(mockExclusiveCar.getDoorNumber());
        Holder<String> brand = new Holder<>("BMWtest");
        Holder<Double> basePricePerDay = new Holder<>(1500d);
        Holder<String> driverEquipment = new Holder<>("TestEq");
        Holder<String> boardPcName = new Holder<>("boardPc");

        List<ExclusiveCarSoap> allCars = exclusiveCarService.getAllExclusiveCars();
        int sizeBeforeAdd = allCars.size();

        exclusiveCarService.addExclusiveCar(constCarId, engineCapacity, vin, doorNumber, brand, basePricePerDay, driverEquipment, boardPcName);

        allCars = exclusiveCarService.getAllExclusiveCars();
        Assert.assertEquals(allCars.size(), sizeBeforeAdd + 1);
    }

    @Test
    public void getExclusiveCarTest() {
        ExclusiveCarSoap exclusiveCarSoap = exclusiveCarService.getExclusiveCar(mockExclusiveCar.getId().toString());

        Assert.assertEquals(exclusiveCarSoap.getId(), mockExclusiveCar.getId().toString());
        Assert.assertEquals(exclusiveCarSoap.getEngineCapacity(), mockExclusiveCar.getEngineCapacity());
        Assert.assertEquals(exclusiveCarSoap.getVin(), mockExclusiveCar.getVin());
        Assert.assertEquals(exclusiveCarSoap.getDoorNumber(), mockExclusiveCar.getDoorNumber());
        Assert.assertEquals(exclusiveCarSoap.getBrand(), mockExclusiveCar.getBrand());
        Assert.assertEquals(exclusiveCarSoap.getBasePricePerDay(), mockExclusiveCar.getBasePricePerDay());
        Assert.assertEquals(exclusiveCarSoap.getDriverEquipment(), mockExclusiveCar.getDriverEquipment());
    }

    @Test
    public void getAllExclusiveCarsTest() {
        List<ExclusiveCarSoap> allCars = exclusiveCarService.getAllExclusiveCars();

        Assert.assertNotEquals(allCars.size(), 0);
    }

    @Test
    public void updateExclusiveCarTest() {
        Holder<String> constCarId = new Holder<>(mockExclusiveCar.getId().toString());
        Holder<Double> engineCapacity = new Holder<>(mockExclusiveCar.getEngineCapacity());
        Holder<String> vin = new Holder<>(mockExclusiveCar.getVin());
        Holder<Integer> doorNumber = new Holder<>(1337);
        Holder<String> brand = new Holder<>("UpdatedBrand");
        Holder<Double> basePricePerDay = new Holder<>(mockExclusiveCar.getBasePricePerDay());
        Holder<String> driverEquipment = new Holder<>(mockExclusiveCar.getDriverEquipment());
        Holder<String> boardPcName = new Holder<>("boardPc");

        List<ExclusiveCarSoap> allCars = exclusiveCarService.getAllExclusiveCars();
        int sizeBeforeAdd = allCars.size();

        exclusiveCarService.updateExclusiveCar(constCarId, engineCapacity, vin, doorNumber, brand, basePricePerDay, driverEquipment, boardPcName);

        allCars = exclusiveCarService.getAllExclusiveCars();
        Assert.assertEquals(allCars.size(), sizeBeforeAdd);


        ExclusiveCarSoap exclusiveCarSoap = exclusiveCarService.getExclusiveCar(mockExclusiveCar.getId().toString());

        Assert.assertEquals(exclusiveCarSoap.getId(), mockExclusiveCar.getId().toString());
        Assert.assertEquals(exclusiveCarSoap.getEngineCapacity(), mockExclusiveCar.getEngineCapacity());
        Assert.assertEquals(exclusiveCarSoap.getVin(), mockExclusiveCar.getVin());
        Assert.assertEquals(exclusiveCarSoap.getDoorNumber(), Integer.toUnsignedLong(doorNumber.value));
        Assert.assertEquals(exclusiveCarSoap.getBrand(), brand.value);
        Assert.assertEquals(exclusiveCarSoap.getBasePricePerDay(), mockExclusiveCar.getBasePricePerDay());
        Assert.assertEquals(exclusiveCarSoap.getDriverEquipment(), mockExclusiveCar.getDriverEquipment());
    }

    @Test
    public void deleteExclusiveCarTest() {
        List<ExclusiveCarSoap> allCars = exclusiveCarService.getAllExclusiveCars();
        int sizeBeforeAdd = allCars.size();

        exclusiveCarService.deleteExclusiveCar(mockExclusiveCar.getId().toString());

        allCars = exclusiveCarService.getAllExclusiveCars();
        Assert.assertEquals(allCars.size(), sizeBeforeAdd - 1);
    }
}

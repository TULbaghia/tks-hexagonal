package pl.lodz.p.it.tks.rent.applicationservices;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pl.lodz.p.it.tks.rent.applicationports.adapters.driven.ExclusiveCarRepositoryAdapter;
import pl.lodz.p.it.tks.rent.applicationports.adapters.driven.RentRepositoryAdapter;
import pl.lodz.p.it.tks.rent.applicationports.exception.RepositoryAdapterException;
import pl.lodz.p.it.tks.rent.domainmodel.resources.ExclusiveCar;
import pl.lodz.p.it.tks.rent.repository.CarEntRepository;
import pl.lodz.p.it.tks.rent.repository.RentEntRepository;import pl.lodz.p.it.tks.rent.repository.UserEntRepository;

import java.util.List;

public class ExclusiveCarServiceTest {

    private ExclusiveCarService exclusiveCarService;
    private ExclusiveCarRepositoryAdapter exclusiveCarRepositoryAdapter;
    private RentRepositoryAdapter rentRepositoryAdapter;

    @BeforeMethod
    public void beforeMethod() {
        CarEntRepository carEntRepository = new CarEntRepository();
        exclusiveCarRepositoryAdapter = new ExclusiveCarRepositoryAdapter(carEntRepository);
        rentRepositoryAdapter = new RentRepositoryAdapter(new RentEntRepository(), new UserEntRepository(), carEntRepository);
        exclusiveCarService = new ExclusiveCarService(exclusiveCarRepositoryAdapter, exclusiveCarRepositoryAdapter, exclusiveCarRepositoryAdapter, exclusiveCarRepositoryAdapter, exclusiveCarRepositoryAdapter, exclusiveCarRepositoryAdapter, rentRepositoryAdapter, rentRepositoryAdapter);
    }

    @Test
    public void addExclusiveCarTest() throws RepositoryAdapterException {
        exclusiveCarService.add(ExclusiveCar.builder().driverEquipment("TEST").brand("Mazda").basePricePerDay(50).doorNumber(5).engineCapacity(998).vin("12345678901234567").boardPcName("").build());
        Assert.assertThrows(RepositoryAdapterException.class, () ->
                exclusiveCarService.add(ExclusiveCar.builder().driverEquipment("TEST").brand("Mazda").basePricePerDay(50).doorNumber(5).engineCapacity(998).vin("12345678901234567").boardPcName("").build()));

        Assert.assertEquals(exclusiveCarRepositoryAdapter.getExclusiveCarByVin("12345678901234567").getDriverEquipment(), "TEST");
        Assert.assertEquals(exclusiveCarRepositoryAdapter.getExclusiveCarByVin("12345678901234567").getBrand(), "Mazda");
        Assert.assertEquals(exclusiveCarRepositoryAdapter.getExclusiveCarByVin("12345678901234567").getBasePricePerDay(), 50);
        Assert.assertEquals(exclusiveCarRepositoryAdapter.getExclusiveCarByVin("12345678901234567").getDoorNumber(), 5);
        Assert.assertEquals(exclusiveCarRepositoryAdapter.getExclusiveCarByVin("12345678901234567").getEngineCapacity(), 998);
    }

    @Test
    public void updateExclusiveCarTest() throws RepositoryAdapterException {
        exclusiveCarService.add(ExclusiveCar.builder().driverEquipment("TEST").brand("Mazda").basePricePerDay(50).doorNumber(5).engineCapacity(998).vin("12345678901234567").boardPcName("").build());

        ExclusiveCar old = exclusiveCarRepositoryAdapter.getExclusiveCarByVin("12345678901234567");
        old.setBrand("Tesla");
        old.setDriverEquipment("Radio");
        old.setEngineCapacity(1244);

        ExclusiveCar update = exclusiveCarRepositoryAdapter.update(old);

        Assert.assertEquals(update.getBrand(), "Tesla");
        Assert.assertEquals(update.getDriverEquipment(), "Radio");
        Assert.assertEquals(update.getEngineCapacity(), 1244);

        Assert.assertEquals(exclusiveCarRepositoryAdapter.getExclusiveCarByVin("12345678901234567").getBrand(), "Tesla");
        Assert.assertEquals(exclusiveCarRepositoryAdapter.getExclusiveCarByVin("12345678901234567").getDriverEquipment(), "Radio");
        Assert.assertEquals(exclusiveCarRepositoryAdapter.getExclusiveCarByVin("12345678901234567").getEngineCapacity(), 1244);
    }

    @Test
    public void getAllExclusiveCarTest() throws RepositoryAdapterException {
        exclusiveCarService.add(ExclusiveCar.builder().driverEquipment("TEST").brand("Mazda").basePricePerDay(50).doorNumber(5).engineCapacity(998).vin("12345678901234561").boardPcName("").build());
        exclusiveCarService.add(ExclusiveCar.builder().driverEquipment("TEST").brand("Mazda").basePricePerDay(50).doorNumber(5).engineCapacity(998).vin("12345678901234562").boardPcName("").build());
        exclusiveCarService.add(ExclusiveCar.builder().driverEquipment("TEST").brand("Mazda").basePricePerDay(50).doorNumber(5).engineCapacity(998).vin("12345678901234563").boardPcName("").build());

        List<ExclusiveCar> adminList = exclusiveCarService.getAll();

        Assert.assertEquals(adminList.size(), 3);
        Assert.assertEquals(adminList.get(0).getVin(), "12345678901234561");
        Assert.assertEquals(adminList.get(1).getVin(), "12345678901234562");
        Assert.assertEquals(adminList.get(2).getVin(), "12345678901234563");
    }

    @Test
    public void getByUUIDExclusiveCarTest() throws RepositoryAdapterException {
        ExclusiveCar a = exclusiveCarService.add(ExclusiveCar.builder().driverEquipment("TEST").brand("Mazda").basePricePerDay(50).doorNumber(5).engineCapacity(998).vin("12345678901234561").boardPcName("").build());

        ExclusiveCar get = exclusiveCarService.get(a.getId());

        Assert.assertEquals(get, a);
    }

    @Test
    public void getByLoginExclusiveCarTest() throws RepositoryAdapterException {
        ExclusiveCar a = exclusiveCarService.add(ExclusiveCar.builder().driverEquipment("TEST").brand("Mazda").basePricePerDay(50).doorNumber(5).engineCapacity(998).vin("12345678901234561").boardPcName("").build());

        ExclusiveCar get = exclusiveCarService.get(a.getVin());

        Assert.assertEquals(get, a);
    }
}

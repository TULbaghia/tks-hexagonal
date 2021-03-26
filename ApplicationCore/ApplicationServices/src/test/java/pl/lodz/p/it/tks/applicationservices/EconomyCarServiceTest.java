package pl.lodz.p.it.tks.applicationservices;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pl.lodz.p.it.tks.applicationports.adapters.driven.EconomyCarRepositoryAdapter;
import pl.lodz.p.it.tks.applicationports.adapters.driven.RentRepositoryAdapter;
import pl.lodz.p.it.tks.applicationports.exception.RepositoryAdapterException;
import pl.lodz.p.it.tks.domainmodel.resources.EconomyCar;
import pl.lodz.p.it.tks.repository.CarEntRepository;
import pl.lodz.p.it.tks.repository.RentEntRepository;
import pl.lodz.p.it.tks.repository.UserEntRepository;

import java.util.List;

public class EconomyCarServiceTest {

    private EconomyCarService economyCarService;
    private EconomyCarRepositoryAdapter economyCarRepositoryAdapter;
    private RentRepositoryAdapter rentRepositoryAdapter;

    @BeforeMethod
    public void beforeMethod() {
        CarEntRepository carEntRepository = new CarEntRepository();
        economyCarRepositoryAdapter = new EconomyCarRepositoryAdapter(carEntRepository);
        rentRepositoryAdapter = new RentRepositoryAdapter(new RentEntRepository(), new UserEntRepository(), carEntRepository);
        economyCarService = new EconomyCarService(economyCarRepositoryAdapter, economyCarRepositoryAdapter, economyCarRepositoryAdapter, economyCarRepositoryAdapter, economyCarRepositoryAdapter, economyCarRepositoryAdapter, rentRepositoryAdapter, rentRepositoryAdapter);
    }

    @Test
    public void addEconomyCarTest() throws RepositoryAdapterException {
        economyCarService.add(EconomyCar.builder().driverEquipment("TEST").brand("Mazda").basePricePerDay(50).doorNumber(5).engineCapacity(998).vin("12345678901234567").build());
        Assert.assertThrows(RepositoryAdapterException.class, () ->
                economyCarService.add(EconomyCar.builder().driverEquipment("TEST").brand("Mazda").basePricePerDay(50).doorNumber(5).engineCapacity(998).vin("12345678901234567").build()));

        Assert.assertEquals(economyCarRepositoryAdapter.getEconomyCarByVin("12345678901234567").getDriverEquipment(), "TEST");
        Assert.assertEquals(economyCarRepositoryAdapter.getEconomyCarByVin("12345678901234567").getBrand(), "Mazda");
        Assert.assertEquals(economyCarRepositoryAdapter.getEconomyCarByVin("12345678901234567").getBasePricePerDay(), 50);
        Assert.assertEquals(economyCarRepositoryAdapter.getEconomyCarByVin("12345678901234567").getDoorNumber(), 5);
        Assert.assertEquals(economyCarRepositoryAdapter.getEconomyCarByVin("12345678901234567").getEngineCapacity(), 998);
    }

    @Test
    public void updateEconomyCarTest() throws RepositoryAdapterException {
        economyCarService.add(EconomyCar.builder().driverEquipment("TEST").brand("Mazda").basePricePerDay(50).doorNumber(5).engineCapacity(998).vin("12345678901234567").build());

        EconomyCar old = economyCarRepositoryAdapter.getEconomyCarByVin("12345678901234567");
        old.setBrand("Tesla");
        old.setDriverEquipment("Radio");
        old.setEngineCapacity(1244);

        EconomyCar update = economyCarRepositoryAdapter.update(old);

        Assert.assertEquals(update.getBrand(), "Tesla");
        Assert.assertEquals(update.getDriverEquipment(), "Radio");
        Assert.assertEquals(update.getEngineCapacity(), 1244);

        Assert.assertEquals(economyCarRepositoryAdapter.getEconomyCarByVin("12345678901234567").getBrand(), "Tesla");
        Assert.assertEquals(economyCarRepositoryAdapter.getEconomyCarByVin("12345678901234567").getDriverEquipment(), "Radio");
        Assert.assertEquals(economyCarRepositoryAdapter.getEconomyCarByVin("12345678901234567").getEngineCapacity(), 1244);
    }

    @Test
    public void getAllEconomyCarTest() throws RepositoryAdapterException {
        economyCarService.add(EconomyCar.builder().driverEquipment("TEST").brand("Mazda").basePricePerDay(50).doorNumber(5).engineCapacity(998).vin("12345678901234561").build());
        economyCarService.add(EconomyCar.builder().driverEquipment("TEST").brand("Mazda").basePricePerDay(50).doorNumber(5).engineCapacity(998).vin("12345678901234562").build());
        economyCarService.add(EconomyCar.builder().driverEquipment("TEST").brand("Mazda").basePricePerDay(50).doorNumber(5).engineCapacity(998).vin("12345678901234563").build());

        List<EconomyCar> adminList = economyCarService.getAll();

        Assert.assertEquals(adminList.size(), 3);
        Assert.assertEquals(adminList.get(0).getVin(), "12345678901234561");
        Assert.assertEquals(adminList.get(1).getVin(), "12345678901234562");
        Assert.assertEquals(adminList.get(2).getVin(), "12345678901234563");
    }

    @Test
    public void getByUUIDEconomyCarTest() throws RepositoryAdapterException {
        EconomyCar a = economyCarService.add(EconomyCar.builder().driverEquipment("TEST").brand("Mazda").basePricePerDay(50).doorNumber(5).engineCapacity(998).vin("12345678901234561").build());

        EconomyCar get = economyCarService.get(a.getId());

        Assert.assertEquals(get, a);
    }

    @Test
    public void getByLoginEconomyCarTest() throws RepositoryAdapterException {
        EconomyCar a = economyCarService.add(EconomyCar.builder().driverEquipment("TEST").brand("Mazda").basePricePerDay(50).doorNumber(5).engineCapacity(998).vin("12345678901234561").build());

        EconomyCar get = economyCarService.get(a.getVin());

        Assert.assertEquals(get, a);
    }
}

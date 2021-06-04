package pl.lodz.p.it.tks.rent.applicationservices;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.lodz.p.it.tks.rent.applicationports.adapters.driven.EconomyCarRepositoryAdapter;
import pl.lodz.p.it.tks.rent.applicationports.adapters.driven.RentRepositoryAdapter;
import pl.lodz.p.it.tks.rent.applicationports.exception.RepositoryAdapterException;
import pl.lodz.p.it.tks.rent.domainmodel.resources.EconomyCar;
import pl.lodz.p.it.tks.rent.repository.CarEntRepository;
import pl.lodz.p.it.tks.rent.repository.RentEntRepository;import pl.lodz.p.it.tks.rent.repository.UserEntRepository;

import java.util.List;

public class EconomyCarServiceTest {

    private static EconomyCarService economyCarService;
    private static EconomyCarRepositoryAdapter economyCarRepositoryAdapter;
    private static RentRepositoryAdapter rentRepositoryAdapter;

    @BeforeEach
    public void beforeMethod() {
        CarEntRepository carEntRepository = new CarEntRepository();
        economyCarRepositoryAdapter = new EconomyCarRepositoryAdapter(carEntRepository);
        rentRepositoryAdapter = new RentRepositoryAdapter(new RentEntRepository(), new UserEntRepository(), carEntRepository);
        economyCarService = new EconomyCarService(economyCarRepositoryAdapter, economyCarRepositoryAdapter, economyCarRepositoryAdapter, economyCarRepositoryAdapter, economyCarRepositoryAdapter, economyCarRepositoryAdapter, rentRepositoryAdapter, rentRepositoryAdapter);
    }

    @Test
    public void addEconomyCarTest() throws RepositoryAdapterException {
        economyCarService.add(EconomyCar.builder().driverEquipment("TEST").brand("Mazda").basePricePerDay(50).doorNumber(5).engineCapacity(998).vin("12345678901234567").build());
        Assertions.assertThrows(RepositoryAdapterException.class, () ->
                economyCarService.add(EconomyCar.builder().driverEquipment("TEST").brand("Mazda").basePricePerDay(50).doorNumber(5).engineCapacity(998).vin("12345678901234567").build()));

        Assertions.assertEquals(economyCarRepositoryAdapter.getEconomyCarByVin("12345678901234567").getDriverEquipment(), "TEST");
        Assertions.assertEquals(economyCarRepositoryAdapter.getEconomyCarByVin("12345678901234567").getBrand(), "Mazda");
        Assertions.assertEquals(economyCarRepositoryAdapter.getEconomyCarByVin("12345678901234567").getBasePricePerDay(), 50);
        Assertions.assertEquals(economyCarRepositoryAdapter.getEconomyCarByVin("12345678901234567").getDoorNumber(), 5);
        Assertions.assertEquals(economyCarRepositoryAdapter.getEconomyCarByVin("12345678901234567").getEngineCapacity(), 998);
    }

    @Test
    public void updateEconomyCarTest() throws RepositoryAdapterException {
        economyCarService.add(EconomyCar.builder().driverEquipment("TEST").brand("Mazda").basePricePerDay(50).doorNumber(5).engineCapacity(998).vin("12345678901234567").build());

        EconomyCar old = economyCarRepositoryAdapter.getEconomyCarByVin("12345678901234567");
        old.setBrand("Tesla");
        old.setDriverEquipment("Radio");
        old.setEngineCapacity(1244);

        EconomyCar update = economyCarRepositoryAdapter.update(old);

        Assertions.assertEquals(update.getBrand(), "Tesla");
        Assertions.assertEquals(update.getDriverEquipment(), "Radio");
        Assertions.assertEquals(update.getEngineCapacity(), 1244);

        Assertions.assertEquals(economyCarRepositoryAdapter.getEconomyCarByVin("12345678901234567").getBrand(), "Tesla");
        Assertions.assertEquals(economyCarRepositoryAdapter.getEconomyCarByVin("12345678901234567").getDriverEquipment(), "Radio");
        Assertions.assertEquals(economyCarRepositoryAdapter.getEconomyCarByVin("12345678901234567").getEngineCapacity(), 1244);
    }

    @Test
    public void getAllEconomyCarTest() throws RepositoryAdapterException {
        economyCarService.add(EconomyCar.builder().driverEquipment("TEST").brand("Mazda").basePricePerDay(50).doorNumber(5).engineCapacity(998).vin("12345678901234561").build());
        economyCarService.add(EconomyCar.builder().driverEquipment("TEST").brand("Mazda").basePricePerDay(50).doorNumber(5).engineCapacity(998).vin("12345678901234562").build());
        economyCarService.add(EconomyCar.builder().driverEquipment("TEST").brand("Mazda").basePricePerDay(50).doorNumber(5).engineCapacity(998).vin("12345678901234563").build());

        List<EconomyCar> adminList = economyCarService.getAll();

        Assertions.assertEquals(adminList.size(), 3);
        Assertions.assertEquals(adminList.get(0).getVin(), "12345678901234561");
        Assertions.assertEquals(adminList.get(1).getVin(), "12345678901234562");
        Assertions.assertEquals(adminList.get(2).getVin(), "12345678901234563");
    }

    @Test
    public void getByUUIDEconomyCarTest() throws RepositoryAdapterException {
        EconomyCar a = economyCarService.add(EconomyCar.builder().driverEquipment("TEST").brand("Mazda").basePricePerDay(50).doorNumber(5).engineCapacity(998).vin("12345678901234561").build());

        EconomyCar get = economyCarService.get(a.getId());

        Assertions.assertEquals(get, a);
    }

    @Test
    public void getByLoginEconomyCarTest() throws RepositoryAdapterException {
        EconomyCar a = economyCarService.add(EconomyCar.builder().driverEquipment("TEST").brand("Mazda").basePricePerDay(50).doorNumber(5).engineCapacity(998).vin("12345678901234561").build());

        EconomyCar get = economyCarService.get(a.getVin());

        Assertions.assertEquals(get, a);
    }
}

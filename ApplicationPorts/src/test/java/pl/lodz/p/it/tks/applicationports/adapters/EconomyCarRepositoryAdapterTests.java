package pl.lodz.p.it.tks.applicationports.adapters;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pl.lodz.p.it.tks.applicationports.adapters.driven.EconomyCarRepositoryAdapter;
import pl.lodz.p.it.tks.applicationports.exception.RepositoryAdapterException;
import pl.lodz.p.it.tks.data.resources.EconomyCarEnt;
import pl.lodz.p.it.tks.domainmodel.resources.EconomyCar;
import pl.lodz.p.it.tks.repository.CarEntRepository;
import pl.lodz.p.it.tks.repository.exception.RepositoryEntException;

import java.lang.reflect.Field;
import java.util.UUID;

public class EconomyCarRepositoryAdapterTests {

    private EconomyCarRepositoryAdapter economyCarRepositoryAdapter;
    private CarEntRepository carEntRepository;

    @BeforeMethod
    public void beforeTest() throws IllegalAccessException, NoSuchFieldException {
        economyCarRepositoryAdapter = new EconomyCarRepositoryAdapter();
        carEntRepository = new CarEntRepository();
        Field field = economyCarRepositoryAdapter.getClass().getDeclaredField("carEntRepository");
        field.setAccessible(true);
        field.set(economyCarRepositoryAdapter, carEntRepository);
    }

    @Test
    public void addEconomyTest() throws RepositoryAdapterException, RepositoryEntException {
        EconomyCar economyCar = EconomyCar.builder()
                .vin("8233h4234h234g2")
                .engineCapacity(1.5)
                .doorNumber(5)
                .brand("BMW")
                .basePricePerDay(1000d)
                .driverEquipment("Immobilizer")
                .build();

        economyCarRepositoryAdapter.add(economyCar);
        Assert.assertEquals(carEntRepository.getAll().size(), 1);
        Assert.assertThrows(RepositoryAdapterException.class, () -> economyCarRepositoryAdapter.add(economyCar));
        Assert.assertEquals(carEntRepository.getAll().size(), 1);
    }

    @Test
    public void getEconomyByIdTest() throws RepositoryAdapterException, RepositoryEntException {
        EconomyCarEnt car = EconomyCarEnt.builder()
                .vin("8233h4234h234g2")
                .engineCapacity(1.5)
                .doorNumber(5)
                .brand("BMW")
                .basePricePerDay(1000d)
                .driverEquipment("Immobilizer")
                .build();

        carEntRepository.add(car);
        Assert.assertEquals(carEntRepository.getAll().size(), 1);
        Assert.assertNotEquals(car.getId(), null);

        EconomyCar getCar = economyCarRepositoryAdapter.get(car.getId());
        Assert.assertEquals(getCar.getId(), car.getId());
        Assert.assertEquals(getCar.getVin(), car.getVin());
        Assert.assertEquals(getCar.getEngineCapacity(), car.getEngineCapacity());
        Assert.assertEquals(getCar.getDoorNumber(), car.getDoorNumber());
        Assert.assertEquals(getCar.getBrand(), car.getBrand());
        Assert.assertEquals(getCar.getBasePricePerDay(), car.getBasePricePerDay());
        Assert.assertEquals(getCar.getDriverEquipment(), car.getDriverEquipment());

        Assert.assertThrows(RepositoryAdapterException.class, () -> economyCarRepositoryAdapter.get(UUID.randomUUID()));
    }

    @Test
    public void getEconomyByLoginTest() throws RepositoryAdapterException, RepositoryEntException {
        EconomyCarEnt car = EconomyCarEnt.builder()
                .vin("8233h4234h234g2")
                .engineCapacity(1.5)
                .doorNumber(5)
                .brand("BMW")
                .basePricePerDay(1000d)
                .driverEquipment("Immobilizer")
                .build();

        carEntRepository.add(car);
        Assert.assertEquals(carEntRepository.getAll().size(), 1);
        Assert.assertNotEquals(car.getId(), null);

        EconomyCar getCar = economyCarRepositoryAdapter.getEconomyCarByVin(car.getVin());
        Assert.assertEquals(getCar.getId(), car.getId());
        Assert.assertEquals(getCar.getVin(), car.getVin());
        Assert.assertEquals(getCar.getEngineCapacity(), car.getEngineCapacity());
        Assert.assertEquals(getCar.getDoorNumber(), car.getDoorNumber());
        Assert.assertEquals(getCar.getBrand(), car.getBrand());
        Assert.assertEquals(getCar.getBasePricePerDay(), car.getBasePricePerDay());
        Assert.assertEquals(getCar.getDriverEquipment(), car.getDriverEquipment());

        Assert.assertThrows(RepositoryAdapterException.class, () -> economyCarRepositoryAdapter.getEconomyCarByVin("VIN"));
    }

    @Test
    public void getAllEconomyTest() throws RepositoryEntException, RepositoryAdapterException {
        int carsCount = 5;
        for (int i = 0; i < carsCount; i++) {
            EconomyCarEnt car = EconomyCarEnt.builder()
                    .vin("1111111111111111" + i)
                    .engineCapacity(1.5)
                    .doorNumber(5)
                    .brand("BMW" + i)
                    .basePricePerDay(1000d)
                    .driverEquipment("Immobilizer")
                    .build();
            carEntRepository.add(car);
        }

        Assert.assertEquals(economyCarRepositoryAdapter.getAll().size(), carsCount);
    }

    @Test
    public void updateEconomyTest() throws RepositoryEntException, RepositoryAdapterException {
        EconomyCarEnt car = EconomyCarEnt.builder()
                .vin("8233h4234h234g2")
                .engineCapacity(1.5)
                .doorNumber(5)
                .brand("BMW")
                .basePricePerDay(1000d)
                .driverEquipment("Immobilizer")
                .build();

        carEntRepository.add(car);
        Assert.assertEquals(carEntRepository.getAll().size(), 1);
        Assert.assertNotEquals(car.getId(), null);

        EconomyCar updatedCar = EconomyCar.builder()
                .id(car.getId())
                .vin(car.getVin())
                .engineCapacity(1.5)
                .doorNumber(5)
                .brand("Ford")
                .basePricePerDay(1000d)
                .driverEquipment("Immobilizer")
                .build();

        economyCarRepositoryAdapter.update(updatedCar);
        Assert.assertEquals(carEntRepository.getAll().size(), 1);

        EconomyCarEnt getUpdatedCar = (EconomyCarEnt) carEntRepository.get(car.getId());

        Assert.assertEquals(getUpdatedCar.getId(), updatedCar.getId());
        Assert.assertEquals(getUpdatedCar.getVin(), updatedCar.getVin());
        Assert.assertEquals(getUpdatedCar.getEngineCapacity(), updatedCar.getEngineCapacity());
        Assert.assertEquals(getUpdatedCar.getDoorNumber(), updatedCar.getDoorNumber());
        Assert.assertEquals(getUpdatedCar.getBrand(), updatedCar.getBrand());
        Assert.assertEquals(getUpdatedCar.getBasePricePerDay(), updatedCar.getBasePricePerDay());
        Assert.assertEquals(getUpdatedCar.getDriverEquipment(), updatedCar.getDriverEquipment());

        EconomyCar noUpdateCar = EconomyCar.builder()
                .vin("11111111111111111")
                .engineCapacity(1.5)
                .doorNumber(5)
                .brand("Ford")
                .basePricePerDay(1000d)
                .driverEquipment("Immobilizer")
                .build();

        Assert.assertThrows(RepositoryAdapterException.class, () -> economyCarRepositoryAdapter.update(noUpdateCar));
    }

    @Test
    public void deleteEconomyTest() throws RepositoryEntException, RepositoryAdapterException {
        EconomyCarEnt car = EconomyCarEnt.builder()
                .vin("8233h4234h234g2")
                .engineCapacity(1.5)
                .doorNumber(5)
                .brand("BMW")
                .basePricePerDay(1000d)
                .driverEquipment("Immobilizer")
                .build();

        carEntRepository.add(car);
        Assert.assertEquals(carEntRepository.getAll().size(), 1);
        Assert.assertNotEquals(car.getId(), null);

        economyCarRepositoryAdapter.delete(car.getId());
        Assert.assertEquals(carEntRepository.getAll().size(), 0);

        Assert.assertThrows(RepositoryAdapterException.class, () -> economyCarRepositoryAdapter.delete(car.getId()));
    }
}

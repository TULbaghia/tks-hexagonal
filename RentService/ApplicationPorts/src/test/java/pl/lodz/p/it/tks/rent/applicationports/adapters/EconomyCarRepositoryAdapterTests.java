package pl.lodz.p.it.tks.rent.applicationports.adapters;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.lodz.p.it.tks.rent.applicationports.adapters.driven.EconomyCarRepositoryAdapter;
import pl.lodz.p.it.tks.rent.applicationports.exception.RepositoryAdapterException;
import pl.lodz.p.it.tks.rent.data.resources.EconomyCarEnt;
import pl.lodz.p.it.tks.rent.domainmodel.resources.EconomyCar;
import pl.lodz.p.it.tks.rent.repository.CarEntRepository;
import pl.lodz.p.it.tks.rent.repository.exception.RepositoryEntException;

import java.lang.reflect.Field;
import java.util.UUID;

public class EconomyCarRepositoryAdapterTests {

    private static EconomyCarRepositoryAdapter economyCarRepositoryAdapter;
    private static CarEntRepository carEntRepository;

    @BeforeEach
    public void beforeTest() throws IllegalAccessException, NoSuchFieldException {
        economyCarRepositoryAdapter = new EconomyCarRepositoryAdapter(new CarEntRepository());
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
        Assertions.assertEquals(carEntRepository.getAll().size(), 1);
        Assertions.assertThrows(RepositoryAdapterException.class, () -> economyCarRepositoryAdapter.add(economyCar));
        Assertions.assertEquals(carEntRepository.getAll().size(), 1);
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
        Assertions.assertEquals(carEntRepository.getAll().size(), 1);
        Assertions.assertNotEquals(car.getId(), null);

        EconomyCar getCar = economyCarRepositoryAdapter.get(car.getId());
        Assertions.assertEquals(getCar.getId(), car.getId());
        Assertions.assertEquals(getCar.getVin(), car.getVin());
        Assertions.assertEquals(getCar.getEngineCapacity(), car.getEngineCapacity());
        Assertions.assertEquals(getCar.getDoorNumber(), car.getDoorNumber());
        Assertions.assertEquals(getCar.getBrand(), car.getBrand());
        Assertions.assertEquals(getCar.getBasePricePerDay(), car.getBasePricePerDay());
        Assertions.assertEquals(getCar.getDriverEquipment(), car.getDriverEquipment());

        Assertions.assertThrows(RepositoryAdapterException.class, () -> economyCarRepositoryAdapter.get(UUID.randomUUID()));
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
        Assertions.assertEquals(carEntRepository.getAll().size(), 1);
        Assertions.assertNotEquals(car.getId(), null);

        EconomyCar getCar = economyCarRepositoryAdapter.getEconomyCarByVin(car.getVin());
        Assertions.assertEquals(getCar.getId(), car.getId());
        Assertions.assertEquals(getCar.getVin(), car.getVin());
        Assertions.assertEquals(getCar.getEngineCapacity(), car.getEngineCapacity());
        Assertions.assertEquals(getCar.getDoorNumber(), car.getDoorNumber());
        Assertions.assertEquals(getCar.getBrand(), car.getBrand());
        Assertions.assertEquals(getCar.getBasePricePerDay(), car.getBasePricePerDay());
        Assertions.assertEquals(getCar.getDriverEquipment(), car.getDriverEquipment());

        Assertions.assertThrows(RepositoryAdapterException.class, () -> economyCarRepositoryAdapter.getEconomyCarByVin("VIN"));
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

        Assertions.assertEquals(economyCarRepositoryAdapter.getAll().size(), carsCount);
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
        Assertions.assertEquals(carEntRepository.getAll().size(), 1);
        Assertions.assertNotEquals(car.getId(), null);

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
        Assertions.assertEquals(carEntRepository.getAll().size(), 1);

        EconomyCarEnt getUpdatedCar = (EconomyCarEnt) carEntRepository.get(car.getId());

        Assertions.assertEquals(getUpdatedCar.getId(), updatedCar.getId());
        Assertions.assertEquals(getUpdatedCar.getVin(), updatedCar.getVin());
        Assertions.assertEquals(getUpdatedCar.getEngineCapacity(), updatedCar.getEngineCapacity());
        Assertions.assertEquals(getUpdatedCar.getDoorNumber(), updatedCar.getDoorNumber());
        Assertions.assertEquals(getUpdatedCar.getBrand(), updatedCar.getBrand());
        Assertions.assertEquals(getUpdatedCar.getBasePricePerDay(), updatedCar.getBasePricePerDay());
        Assertions.assertEquals(getUpdatedCar.getDriverEquipment(), updatedCar.getDriverEquipment());

        EconomyCar noUpdateCar = EconomyCar.builder()
                .vin("11111111111111111")
                .engineCapacity(1.5)
                .doorNumber(5)
                .brand("Ford")
                .basePricePerDay(1000d)
                .driverEquipment("Immobilizer")
                .build();

        Assertions.assertThrows(RepositoryAdapterException.class, () -> economyCarRepositoryAdapter.update(noUpdateCar));
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
        Assertions.assertEquals(carEntRepository.getAll().size(), 1);
        Assertions.assertNotEquals(car.getId(), null);

        economyCarRepositoryAdapter.delete(car.getId());
        Assertions.assertEquals(carEntRepository.getAll().size(), 0);

        Assertions.assertThrows(RepositoryAdapterException.class, () -> economyCarRepositoryAdapter.delete(car.getId()));
    }
}

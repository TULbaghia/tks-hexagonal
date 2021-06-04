package pl.lodz.p.it.tks.rent.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.lodz.p.it.tks.rent.data.resources.CarEnt;
import pl.lodz.p.it.tks.rent.data.resources.EconomyCarEnt;
import pl.lodz.p.it.tks.rent.repository.exception.RepositoryEntException;

import java.util.UUID;

public class CarEntRepositoryTests {

    private static CarEntRepository carEntRepository;

    @BeforeEach
    public void beforeTest() {
        carEntRepository = new CarEntRepository();
    }

    @Test
    public void addCarTest() throws RepositoryEntException {
        CarEnt car = EconomyCarEnt.builder()
                .vin("8233h4234h234g2")
                .engineCapacity(1.5)
                .doorNumber(5)
                .brand("BMW")
                .basePricePerDay(1000d)
                .driverEquipment("Immobilizer")
                .build();

        carEntRepository.add(car);
        Assertions.assertEquals(carEntRepository.getAll().size(), 1);
        Assertions.assertThrows(RepositoryEntException.class, () -> carEntRepository.add(car));
        Assertions.assertEquals(carEntRepository.getAll().size(), 1);
    }

    @Test
    public void getCarByUuidTest() throws RepositoryEntException {
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

        EconomyCarEnt getCar = (EconomyCarEnt) carEntRepository.get(car.getId());
        Assertions.assertEquals(getCar.getId(), car.getId());
        Assertions.assertEquals(getCar.getVin(), car.getVin());
        Assertions.assertEquals(getCar.getEngineCapacity(), car.getEngineCapacity());
        Assertions.assertEquals(getCar.getDoorNumber(), car.getDoorNumber());
        Assertions.assertEquals(getCar.getBrand(), car.getBrand());
        Assertions.assertEquals(getCar.getBasePricePerDay(), car.getBasePricePerDay());
        Assertions.assertEquals(getCar.getDriverEquipment(), car.getDriverEquipment());

        Assertions.assertThrows(RepositoryEntException.class, () -> carEntRepository.get(UUID.randomUUID()));
    }

    @Test
    public void getCarByVinTest() throws RepositoryEntException {
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

        EconomyCarEnt getCar = (EconomyCarEnt) carEntRepository.get(car.getVin());
        Assertions.assertEquals(getCar.getId(), car.getId());
        Assertions.assertEquals(getCar.getVin(), car.getVin());
        Assertions.assertEquals(getCar.getEngineCapacity(), car.getEngineCapacity());
        Assertions.assertEquals(getCar.getDoorNumber(), car.getDoorNumber());
        Assertions.assertEquals(getCar.getBrand(), car.getBrand());
        Assertions.assertEquals(getCar.getBasePricePerDay(), car.getBasePricePerDay());
        Assertions.assertEquals(getCar.getDriverEquipment(), car.getDriverEquipment());

        Assertions.assertThrows(RepositoryEntException.class, () -> carEntRepository.get("VIN"));
    }

    @Test
    public void getAllCarTest() throws RepositoryEntException {
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

        Assertions.assertEquals(carEntRepository.getAll().size(), carsCount);
    }

    @Test
    public void deleteCarTest() throws RepositoryEntException {
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

        carEntRepository.delete(car.getId());
        Assertions.assertEquals(carEntRepository.getAll().size(), 0);

        Assertions.assertThrows(RepositoryEntException.class, () -> carEntRepository.delete(car.getId()));
    }

    @Test
    public void updateCarTest() throws RepositoryEntException {
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

        EconomyCarEnt updatedCar = EconomyCarEnt.builder()
                .id(car.getId())
                .vin(car.getVin())
                .engineCapacity(1.5)
                .doorNumber(5)
                .brand("Ford")
                .basePricePerDay(1000d)
                .driverEquipment("Immobilizer")
                .build();

        carEntRepository.update(updatedCar);
        Assertions.assertEquals(carEntRepository.getAll().size(), 1);

        EconomyCarEnt getUpdatedCar = (EconomyCarEnt) carEntRepository.get(car.getId());

        Assertions.assertEquals(getUpdatedCar.getId(), updatedCar.getId());
        Assertions.assertEquals(getUpdatedCar.getVin(), updatedCar.getVin());
        Assertions.assertEquals(getUpdatedCar.getEngineCapacity(), updatedCar.getEngineCapacity());
        Assertions.assertEquals(getUpdatedCar.getDoorNumber(), updatedCar.getDoorNumber());
        Assertions.assertEquals(getUpdatedCar.getBrand(), updatedCar.getBrand());
        Assertions.assertEquals(getUpdatedCar.getBasePricePerDay(), updatedCar.getBasePricePerDay());
        Assertions.assertEquals(getUpdatedCar.getDriverEquipment(), updatedCar.getDriverEquipment());

        Assertions.assertThrows(RepositoryEntException.class, () -> carEntRepository.update(new EconomyCarEnt()));
    }
}

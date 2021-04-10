package pl.lodz.p.it.tks.repository;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pl.lodz.p.it.tks.data.resources.CarEnt;
import pl.lodz.p.it.tks.data.resources.EconomyCarEnt;
import pl.lodz.p.it.tks.repository.exception.RepositoryEntException;

import java.util.UUID;

public class CarEntRepositoryTests {

    private CarEntRepository carEntRepository;

    @BeforeMethod
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
        Assert.assertEquals(carEntRepository.getAll().size(), 1);
        Assert.assertThrows(RepositoryEntException.class, () -> carEntRepository.add(car));
        Assert.assertEquals(carEntRepository.getAll().size(), 1);
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
        Assert.assertEquals(carEntRepository.getAll().size(), 1);
        Assert.assertNotEquals(car.getId(), null);

        EconomyCarEnt getCar = (EconomyCarEnt) carEntRepository.get(car.getId());
        Assert.assertEquals(getCar.getId(), car.getId());
        Assert.assertEquals(getCar.getVin(), car.getVin());
        Assert.assertEquals(getCar.getEngineCapacity(), car.getEngineCapacity());
        Assert.assertEquals(getCar.getDoorNumber(), car.getDoorNumber());
        Assert.assertEquals(getCar.getBrand(), car.getBrand());
        Assert.assertEquals(getCar.getBasePricePerDay(), car.getBasePricePerDay());
        Assert.assertEquals(getCar.getDriverEquipment(), car.getDriverEquipment());

        Assert.assertThrows(RepositoryEntException.class, () -> carEntRepository.get(UUID.randomUUID()));
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
        Assert.assertEquals(carEntRepository.getAll().size(), 1);
        Assert.assertNotEquals(car.getId(), null);

        EconomyCarEnt getCar = (EconomyCarEnt) carEntRepository.get(car.getVin());
        Assert.assertEquals(getCar.getId(), car.getId());
        Assert.assertEquals(getCar.getVin(), car.getVin());
        Assert.assertEquals(getCar.getEngineCapacity(), car.getEngineCapacity());
        Assert.assertEquals(getCar.getDoorNumber(), car.getDoorNumber());
        Assert.assertEquals(getCar.getBrand(), car.getBrand());
        Assert.assertEquals(getCar.getBasePricePerDay(), car.getBasePricePerDay());
        Assert.assertEquals(getCar.getDriverEquipment(), car.getDriverEquipment());

        Assert.assertThrows(RepositoryEntException.class, () -> carEntRepository.get("VIN"));
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

        Assert.assertEquals(carEntRepository.getAll().size(), carsCount);
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
        Assert.assertEquals(carEntRepository.getAll().size(), 1);
        Assert.assertNotEquals(car.getId(), null);

        carEntRepository.delete(car.getId());
        Assert.assertEquals(carEntRepository.getAll().size(), 0);

        Assert.assertThrows(RepositoryEntException.class, () -> carEntRepository.delete(car.getId()));
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
        Assert.assertEquals(carEntRepository.getAll().size(), 1);
        Assert.assertNotEquals(car.getId(), null);

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
        Assert.assertEquals(carEntRepository.getAll().size(), 1);

        EconomyCarEnt getUpdatedCar = (EconomyCarEnt) carEntRepository.get(car.getId());

        Assert.assertEquals(getUpdatedCar.getId(), updatedCar.getId());
        Assert.assertEquals(getUpdatedCar.getVin(), updatedCar.getVin());
        Assert.assertEquals(getUpdatedCar.getEngineCapacity(), updatedCar.getEngineCapacity());
        Assert.assertEquals(getUpdatedCar.getDoorNumber(), updatedCar.getDoorNumber());
        Assert.assertEquals(getUpdatedCar.getBrand(), updatedCar.getBrand());
        Assert.assertEquals(getUpdatedCar.getBasePricePerDay(), updatedCar.getBasePricePerDay());
        Assert.assertEquals(getUpdatedCar.getDriverEquipment(), updatedCar.getDriverEquipment());

        Assert.assertThrows(RepositoryEntException.class, () -> carEntRepository.update(new EconomyCarEnt()));
    }
}

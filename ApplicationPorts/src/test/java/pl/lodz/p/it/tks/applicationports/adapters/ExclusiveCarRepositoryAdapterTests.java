package pl.lodz.p.it.tks.applicationports.adapters;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pl.lodz.p.it.tks.applicationports.adapters.driven.ExclusiveCarRepositoryAdapter;
import pl.lodz.p.it.tks.applicationports.exception.RepositoryAdapterException;
import pl.lodz.p.it.tks.data.resources.ExclusiveCarEnt;
import pl.lodz.p.it.tks.domainmodel.resources.ExclusiveCar;
import pl.lodz.p.it.tks.repository.CarEntRepository;
import pl.lodz.p.it.tks.repository.UserEntRepository;
import pl.lodz.p.it.tks.repository.exception.RepositoryEntException;

import java.lang.reflect.Field;
import java.util.UUID;

public class ExclusiveCarRepositoryAdapterTests {
    private ExclusiveCarRepositoryAdapter exclusiveCarRepositoryAdapter;
    private CarEntRepository carEntRepository;

    @BeforeMethod
    public void beforeTest() throws IllegalAccessException, NoSuchFieldException {
        exclusiveCarRepositoryAdapter = new ExclusiveCarRepositoryAdapter(new CarEntRepository());
        carEntRepository = new CarEntRepository();
        Field field = exclusiveCarRepositoryAdapter.getClass().getDeclaredField("carEntRepository");
        field.setAccessible(true);
        field.set(exclusiveCarRepositoryAdapter, carEntRepository);
    }

    @Test
    public void addExclusiveTest() throws RepositoryAdapterException, RepositoryEntException {
        ExclusiveCar exclusiveCar = ExclusiveCar.builder()
                .vin("8233h4234h234g2")
                .engineCapacity(1.5)
                .doorNumber(5)
                .brand("BMW")
                .basePricePerDay(1000d)
                .driverEquipment("Immobilizer")
                .boardPcName("boardPc")
                .build();

        exclusiveCarRepositoryAdapter.add(exclusiveCar);
        Assert.assertEquals(carEntRepository.getAll().size(), 1);
        Assert.assertThrows(RepositoryAdapterException.class, () -> exclusiveCarRepositoryAdapter.add(exclusiveCar));
        Assert.assertEquals(carEntRepository.getAll().size(), 1);
    }

    @Test
    public void getExclusiveByIdTest() throws RepositoryAdapterException, RepositoryEntException {
        ExclusiveCarEnt car = ExclusiveCarEnt.builder()
                .vin("8233h4234h234g2")
                .engineCapacity(1.5)
                .doorNumber(5)
                .brand("BMW")
                .basePricePerDay(1000d)
                .driverEquipment("Immobilizer")
                .boardPcName("boardPc")
                .build();

        carEntRepository.add(car);
        Assert.assertEquals(carEntRepository.getAll().size(), 1);
        Assert.assertNotEquals(car.getId(), null);

        ExclusiveCar getCar = exclusiveCarRepositoryAdapter.get(car.getId());
        Assert.assertEquals(getCar.getId(), car.getId());
        Assert.assertEquals(getCar.getVin(), car.getVin());
        Assert.assertEquals(getCar.getEngineCapacity(), car.getEngineCapacity());
        Assert.assertEquals(getCar.getDoorNumber(), car.getDoorNumber());
        Assert.assertEquals(getCar.getBrand(), car.getBrand());
        Assert.assertEquals(getCar.getBasePricePerDay(), car.getBasePricePerDay());
        Assert.assertEquals(getCar.getDriverEquipment(), car.getDriverEquipment());
        Assert.assertEquals(getCar.getBoardPcName(), car.getBoardPcName());

        Assert.assertThrows(RepositoryAdapterException.class, () -> exclusiveCarRepositoryAdapter.get(UUID.randomUUID()));
    }

    @Test
    public void getExclusiveByLoginTest() throws RepositoryAdapterException, RepositoryEntException {
        ExclusiveCarEnt car = ExclusiveCarEnt.builder()
                .vin("8233h4234h234g2")
                .engineCapacity(1.5)
                .doorNumber(5)
                .brand("BMW")
                .basePricePerDay(1000d)
                .driverEquipment("Immobilizer")
                .boardPcName("boardPc")
                .build();

        carEntRepository.add(car);
        Assert.assertEquals(carEntRepository.getAll().size(), 1);
        Assert.assertNotEquals(car.getId(), null);

        ExclusiveCar getCar = exclusiveCarRepositoryAdapter.getExclusiveCarByVin(car.getVin());
        Assert.assertEquals(getCar.getId(), car.getId());
        Assert.assertEquals(getCar.getVin(), car.getVin());
        Assert.assertEquals(getCar.getEngineCapacity(), car.getEngineCapacity());
        Assert.assertEquals(getCar.getDoorNumber(), car.getDoorNumber());
        Assert.assertEquals(getCar.getBrand(), car.getBrand());
        Assert.assertEquals(getCar.getBasePricePerDay(), car.getBasePricePerDay());
        Assert.assertEquals(getCar.getDriverEquipment(), car.getDriverEquipment());
        Assert.assertEquals(getCar.getBoardPcName(), car.getBoardPcName());

        Assert.assertThrows(RepositoryAdapterException.class, () -> exclusiveCarRepositoryAdapter.getExclusiveCarByVin("VIN"));
    }

    @Test
    public void getAllExclusiveTest() throws RepositoryEntException, RepositoryAdapterException {
        int carsCount = 5;
        for (int i = 0; i < carsCount; i++) {
            ExclusiveCarEnt car = ExclusiveCarEnt.builder()
                    .vin("1111111111111111" + i)
                    .engineCapacity(1.5)
                    .doorNumber(5)
                    .brand("BMW" + i)
                    .basePricePerDay(1000d)
                    .driverEquipment("Immobilizer")
                    .boardPcName("boardPc")
                    .build();
            carEntRepository.add(car);
        }

        Assert.assertEquals(exclusiveCarRepositoryAdapter.getAll().size(), carsCount);
    }

    @Test
    public void updateExclusiveTest() throws RepositoryEntException, RepositoryAdapterException {
        ExclusiveCarEnt car = ExclusiveCarEnt.builder()
                .vin("8233h4234h234g2")
                .engineCapacity(1.5)
                .doorNumber(5)
                .brand("BMW")
                .basePricePerDay(1000d)
                .driverEquipment("Immobilizer")
                .boardPcName("boardPc")
                .build();

        carEntRepository.add(car);
        Assert.assertEquals(carEntRepository.getAll().size(), 1);
        Assert.assertNotEquals(car.getId(), null);

        ExclusiveCar updatedCar = ExclusiveCar.builder()
                .id(car.getId())
                .vin(car.getVin())
                .engineCapacity(1.5)
                .doorNumber(5)
                .brand("Ford")
                .basePricePerDay(1000d)
                .driverEquipment("Immobilizer")
                .boardPcName("boardPc")
                .build();

        exclusiveCarRepositoryAdapter.update(updatedCar);
        Assert.assertEquals(carEntRepository.getAll().size(), 1);

        ExclusiveCarEnt getUpdatedCar = (ExclusiveCarEnt) carEntRepository.get(car.getId());

        Assert.assertEquals(getUpdatedCar.getId(), updatedCar.getId());
        Assert.assertEquals(getUpdatedCar.getVin(), updatedCar.getVin());
        Assert.assertEquals(getUpdatedCar.getEngineCapacity(), updatedCar.getEngineCapacity());
        Assert.assertEquals(getUpdatedCar.getDoorNumber(), updatedCar.getDoorNumber());
        Assert.assertEquals(getUpdatedCar.getBrand(), updatedCar.getBrand());
        Assert.assertEquals(getUpdatedCar.getBasePricePerDay(), updatedCar.getBasePricePerDay());
        Assert.assertEquals(getUpdatedCar.getDriverEquipment(), updatedCar.getDriverEquipment());
        Assert.assertEquals(getUpdatedCar.getBoardPcName(), updatedCar.getBoardPcName());

        ExclusiveCar noUpdateCar = ExclusiveCar.builder()
                .vin("11111111111111111")
                .engineCapacity(1.5)
                .doorNumber(5)
                .brand("Ford")
                .basePricePerDay(1000d)
                .driverEquipment("Immobilizer")
                .boardPcName("boardPc")
                .build();

        Assert.assertThrows(RepositoryAdapterException.class, () -> exclusiveCarRepositoryAdapter.update(noUpdateCar));
    }

    @Test
    public void deleteExclusiveTest() throws RepositoryEntException, RepositoryAdapterException {
        ExclusiveCarEnt car = ExclusiveCarEnt.builder()
                .vin("8233h4234h234g2")
                .engineCapacity(1.5)
                .doorNumber(5)
                .brand("BMW")
                .basePricePerDay(1000d)
                .driverEquipment("Immobilizer")
                .boardPcName("boardPc")
                .build();

        carEntRepository.add(car);
        Assert.assertEquals(carEntRepository.getAll().size(), 1);
        Assert.assertNotEquals(car.getId(), null);

        exclusiveCarRepositoryAdapter.delete(car.getId());
        Assert.assertEquals(carEntRepository.getAll().size(), 0);

        Assert.assertThrows(RepositoryAdapterException.class, () -> exclusiveCarRepositoryAdapter.delete(car.getId()));
    }
}

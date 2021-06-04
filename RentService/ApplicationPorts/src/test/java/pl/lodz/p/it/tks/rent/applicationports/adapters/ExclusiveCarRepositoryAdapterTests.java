package pl.lodz.p.it.tks.rent.applicationports.adapters;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.lodz.p.it.tks.rent.applicationports.adapters.driven.ExclusiveCarRepositoryAdapter;
import pl.lodz.p.it.tks.rent.applicationports.exception.RepositoryAdapterException;
import pl.lodz.p.it.tks.rent.data.resources.ExclusiveCarEnt;
import pl.lodz.p.it.tks.rent.domainmodel.resources.ExclusiveCar;
import pl.lodz.p.it.tks.rent.repository.CarEntRepository;
import pl.lodz.p.it.tks.rent.repository.exception.RepositoryEntException;

import java.lang.reflect.Field;
import java.util.UUID;

public class ExclusiveCarRepositoryAdapterTests {
    private static ExclusiveCarRepositoryAdapter exclusiveCarRepositoryAdapter;
    private static CarEntRepository carEntRepository;

    @BeforeEach
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
        Assertions.assertEquals(carEntRepository.getAll().size(), 1);
        Assertions.assertThrows(RepositoryAdapterException.class, () -> exclusiveCarRepositoryAdapter.add(exclusiveCar));
        Assertions.assertEquals(carEntRepository.getAll().size(), 1);
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
        Assertions.assertEquals(carEntRepository.getAll().size(), 1);
        Assertions.assertNotEquals(car.getId(), null);

        ExclusiveCar getCar = exclusiveCarRepositoryAdapter.get(car.getId());
        Assertions.assertEquals(getCar.getId(), car.getId());
        Assertions.assertEquals(getCar.getVin(), car.getVin());
        Assertions.assertEquals(getCar.getEngineCapacity(), car.getEngineCapacity());
        Assertions.assertEquals(getCar.getDoorNumber(), car.getDoorNumber());
        Assertions.assertEquals(getCar.getBrand(), car.getBrand());
        Assertions.assertEquals(getCar.getBasePricePerDay(), car.getBasePricePerDay());
        Assertions.assertEquals(getCar.getDriverEquipment(), car.getDriverEquipment());
        Assertions.assertEquals(getCar.getBoardPcName(), car.getBoardPcName());

        Assertions.assertThrows(RepositoryAdapterException.class, () -> exclusiveCarRepositoryAdapter.get(UUID.randomUUID()));
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
        Assertions.assertEquals(carEntRepository.getAll().size(), 1);
        Assertions.assertNotEquals(car.getId(), null);

        ExclusiveCar getCar = exclusiveCarRepositoryAdapter.getExclusiveCarByVin(car.getVin());
        Assertions.assertEquals(getCar.getId(), car.getId());
        Assertions.assertEquals(getCar.getVin(), car.getVin());
        Assertions.assertEquals(getCar.getEngineCapacity(), car.getEngineCapacity());
        Assertions.assertEquals(getCar.getDoorNumber(), car.getDoorNumber());
        Assertions.assertEquals(getCar.getBrand(), car.getBrand());
        Assertions.assertEquals(getCar.getBasePricePerDay(), car.getBasePricePerDay());
        Assertions.assertEquals(getCar.getDriverEquipment(), car.getDriverEquipment());
        Assertions.assertEquals(getCar.getBoardPcName(), car.getBoardPcName());

        Assertions.assertThrows(RepositoryAdapterException.class, () -> exclusiveCarRepositoryAdapter.getExclusiveCarByVin("VIN"));
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

        Assertions.assertEquals(exclusiveCarRepositoryAdapter.getAll().size(), carsCount);
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
        Assertions.assertEquals(carEntRepository.getAll().size(), 1);
        Assertions.assertNotEquals(car.getId(), null);

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
        Assertions.assertEquals(carEntRepository.getAll().size(), 1);

        ExclusiveCarEnt getUpdatedCar = (ExclusiveCarEnt) carEntRepository.get(car.getId());

        Assertions.assertEquals(getUpdatedCar.getId(), updatedCar.getId());
        Assertions.assertEquals(getUpdatedCar.getVin(), updatedCar.getVin());
        Assertions.assertEquals(getUpdatedCar.getEngineCapacity(), updatedCar.getEngineCapacity());
        Assertions.assertEquals(getUpdatedCar.getDoorNumber(), updatedCar.getDoorNumber());
        Assertions.assertEquals(getUpdatedCar.getBrand(), updatedCar.getBrand());
        Assertions.assertEquals(getUpdatedCar.getBasePricePerDay(), updatedCar.getBasePricePerDay());
        Assertions.assertEquals(getUpdatedCar.getDriverEquipment(), updatedCar.getDriverEquipment());
        Assertions.assertEquals(getUpdatedCar.getBoardPcName(), updatedCar.getBoardPcName());

        ExclusiveCar noUpdateCar = ExclusiveCar.builder()
                .vin("11111111111111111")
                .engineCapacity(1.5)
                .doorNumber(5)
                .brand("Ford")
                .basePricePerDay(1000d)
                .driverEquipment("Immobilizer")
                .boardPcName("boardPc")
                .build();

        Assertions.assertThrows(RepositoryAdapterException.class, () -> exclusiveCarRepositoryAdapter.update(noUpdateCar));
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
        Assertions.assertEquals(carEntRepository.getAll().size(), 1);
        Assertions.assertNotEquals(car.getId(), null);

        exclusiveCarRepositoryAdapter.delete(car.getId());
        Assertions.assertEquals(carEntRepository.getAll().size(), 0);

        Assertions.assertThrows(RepositoryAdapterException.class, () -> exclusiveCarRepositoryAdapter.delete(car.getId()));
    }
}

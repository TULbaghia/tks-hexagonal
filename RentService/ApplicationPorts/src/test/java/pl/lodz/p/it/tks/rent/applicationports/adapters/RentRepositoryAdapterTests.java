package pl.lodz.p.it.tks.rent.applicationports.adapters;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.lodz.p.it.tks.rent.applicationports.adapters.driven.RentRepositoryAdapter;
import pl.lodz.p.it.tks.rent.applicationports.converters.CarConverter;
import pl.lodz.p.it.tks.rent.applicationports.converters.RentConverter;
import pl.lodz.p.it.tks.rent.applicationports.converters.UserConverter;
import pl.lodz.p.it.tks.rent.applicationports.exception.RepositoryAdapterException;
import pl.lodz.p.it.tks.rent.data.resources.CarEnt;
import pl.lodz.p.it.tks.rent.data.resources.EconomyCarEnt;
import pl.lodz.p.it.tks.rent.data.resources.RentEnt;
import pl.lodz.p.it.tks.rent.data.user.CustomerEnt;
import pl.lodz.p.it.tks.rent.domainmodel.resources.Car;
import pl.lodz.p.it.tks.rent.domainmodel.resources.Rent;
import pl.lodz.p.it.tks.rent.domainmodel.user.Customer;
import pl.lodz.p.it.tks.rent.repository.CarEntRepository;
import pl.lodz.p.it.tks.rent.repository.RentEntRepository;
import pl.lodz.p.it.tks.rent.repository.UserEntRepository;
import pl.lodz.p.it.tks.rent.repository.exception.RepositoryEntException;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.UUID;

public class RentRepositoryAdapterTests {
    private static RentRepositoryAdapter rentRepositoryAdapter;

    private static RentEntRepository rentEntRepository;
    private static CarEntRepository carEntRepository;
    private static UserEntRepository userEntRepository;

    @BeforeEach
    public void beforeTest() throws IllegalAccessException, NoSuchFieldException, RepositoryEntException {
        rentRepositoryAdapter = new RentRepositoryAdapter(new RentEntRepository(), new UserEntRepository(), new CarEntRepository());

        rentEntRepository = new RentEntRepository();
        carEntRepository = new CarEntRepository();
        userEntRepository = new UserEntRepository();

        Field rentEntRepositoryField = rentRepositoryAdapter.getClass().getDeclaredField("rentEntRepository");
        Field carEntRepositoryField = rentRepositoryAdapter.getClass().getDeclaredField("carEntRepository");
        Field userEntRepositoryField = rentRepositoryAdapter.getClass().getDeclaredField("userEntRepository");

        rentEntRepositoryField.setAccessible(true);
        carEntRepositoryField.setAccessible(true);
        userEntRepositoryField.setAccessible(true);

        rentEntRepositoryField.set(rentRepositoryAdapter, rentEntRepository);
        carEntRepositoryField.set(rentRepositoryAdapter, carEntRepository);
        userEntRepositoryField.set(rentRepositoryAdapter, userEntRepository);

        int itemsCount = 5;
        for (int i = 0; i < itemsCount; i++) {
            EconomyCarEnt car = EconomyCarEnt.builder()
                    .vin("1111111111111111" + i)
                    .engineCapacity(1.5)
                    .doorNumber(5)
                    .brand("BMW" + i)
                    .basePricePerDay(1000d)
                    .driverEquipment("Immobilizer")
                    .build();

            CustomerEnt customerEnt = CustomerEnt.builder()
                    .firstname("Customer")
                    .lastname("Kowalski")
                    .login("Klient" + i)
                    .build();

            userEntRepository.add(customerEnt);
            carEntRepository.add(car);
        }
    }

    private Rent createRent(int customerNo, int carNo) throws RepositoryEntException {
        Customer customer = UserConverter.convertEntToDomain((CustomerEnt) userEntRepository.get("Klient" + customerNo));
        Car car = CarConverter.convertEntToDomain( (EconomyCarEnt) carEntRepository.get("1111111111111111" + carNo));
        return Rent.builder()
                .car(car)
                .customer(customer)
                .build();
    }

    private RentEnt createRentEnt(int customerNo, int carNo) throws RepositoryEntException {
        CustomerEnt customerEnt = (CustomerEnt) userEntRepository.get("Klient" + customerNo);
        CarEnt carEnt  = carEntRepository.get("1111111111111111" + carNo);
        return RentEnt.builder()
                .car(carEnt)
                .customer(customerEnt)
                .build();
    }

    @Test
    public void addRentTest() throws RepositoryAdapterException, RepositoryEntException {
        Rent rent = createRent(1, 1);

        Rent addedRent = rentRepositoryAdapter.add(rent);
        rent.setId(addedRent.getId());
        Assertions.assertEquals(rentEntRepository.getAll().size(), 1);
//        Assertions.assertThrows(RepositoryAdapterException.class, () -> rentRepositoryAdapter.add(rent));
        Assertions.assertEquals(rentEntRepository.getAll().size(), 1);
    }

    @Test
    public void getRentTest() throws RepositoryAdapterException, RepositoryEntException {
        RentEnt rentEnt = createRentEnt(1, 1);

        rentEntRepository.add(rentEnt);
        Assertions.assertEquals(rentEntRepository.getAll().size(), 1);
        Assertions.assertNotEquals(rentEnt.getId(), null);

        Rent rent = rentRepositoryAdapter.get(rentEnt.getId());
        Assertions.assertEquals(rent.getId(), rentEnt.getId());
        Assertions.assertEquals(rent.getCar().getId(), rentEnt.getCar().getId());
        Assertions.assertEquals(rent.getCustomer().getId(), rentEnt.getCustomer().getId());
        Assertions.assertEquals(rent.getRentStartDate(), rentEnt.getRentStartDate());
        Assertions.assertEquals(rent.getPrice(), rentEnt.getPrice());
        Assertions.assertThrows(RepositoryAdapterException.class, () -> rentRepositoryAdapter.get(UUID.randomUUID()));
    }

    @Test
    public void getAllRentTest() throws RepositoryEntException {
        int rentsCount = 5;
        for (int i = 0; i < rentsCount; i++) {
            RentEnt rentEnt = createRentEnt(i, i);
            rentEntRepository.add(rentEnt);
        }

        Assertions.assertEquals(rentRepositoryAdapter.getAll().size(), rentsCount);
    }

    @Test
    public void updateRentTest() throws RepositoryEntException, RepositoryAdapterException {
        RentEnt rentEnt = createRentEnt(1, 1);

        rentEntRepository.add(rentEnt);
        Assertions.assertEquals(rentEntRepository.getAll().size(), 1);
        Assertions.assertNotEquals(rentEnt.getId(), null);
        rentEnt.setRentStartDate(LocalDateTime.now().plusDays(2));
        rentEnt.setRentEndDate(LocalDateTime.now().plusDays(10));

        Rent rent = RentConverter.convertEntToDomain(rentEnt);

        rentRepositoryAdapter.update(rent);
        Assertions.assertEquals(rentEntRepository.getAll().size(), 1);

        RentEnt getUpdatedRent = rentEntRepository.get(rentEnt.getId());
        Assertions.assertEquals(getUpdatedRent.getId(), rentEnt.getId());
        Assertions.assertEquals(getUpdatedRent.getCar().getId(), rentEnt.getCar().getId());
        Assertions.assertSame(getUpdatedRent.getCar(), rentEnt.getCar());
        Assertions.assertEquals(getUpdatedRent.getCustomer().getId(), rentEnt.getCustomer().getId());
        Assertions.assertSame(getUpdatedRent.getCustomer(), rentEnt.getCustomer());
        Assertions.assertEquals(getUpdatedRent.getRentStartDate(), rentEnt.getRentStartDate());
        Assertions.assertEquals(getUpdatedRent.getRentEndDate(), rentEnt.getRentEndDate());
        Assertions.assertEquals(getUpdatedRent.getPrice(), rentEnt.getPrice());

        Rent noUpdateRent = createRent(1, 1);
        Assertions.assertThrows(RepositoryAdapterException.class, () -> rentRepositoryAdapter.update(noUpdateRent));
    }

    @Test
    public void deleteRentTest() throws RepositoryEntException, RepositoryAdapterException {
        RentEnt rentEnt = createRentEnt(1, 1);

        rentEntRepository.add(rentEnt);
        Assertions.assertEquals(rentEntRepository.getAll().size(), 1);
        Assertions.assertNotEquals(rentEnt.getId(), null);

        rentRepositoryAdapter.delete(rentEnt.getId());
        Assertions.assertEquals(rentEntRepository.getAll().size(), 0);

        Assertions.assertThrows(RepositoryAdapterException.class, () -> rentRepositoryAdapter.delete(rentEnt.getId()));
    }
}

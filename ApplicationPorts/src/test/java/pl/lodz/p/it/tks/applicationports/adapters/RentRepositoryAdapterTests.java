package pl.lodz.p.it.tks.applicationports.adapters;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pl.lodz.p.it.tks.applicationports.adapters.driven.RentRepositoryAdapter;
import pl.lodz.p.it.tks.applicationports.converters.CarConverter;
import pl.lodz.p.it.tks.applicationports.converters.RentConverter;
import pl.lodz.p.it.tks.applicationports.converters.UserConverter;
import pl.lodz.p.it.tks.applicationports.exception.RepositoryAdapterException;
import pl.lodz.p.it.tks.data.resources.CarEnt;
import pl.lodz.p.it.tks.data.resources.EconomyCarEnt;
import pl.lodz.p.it.tks.data.resources.RentEnt;
import pl.lodz.p.it.tks.data.user.CustomerEnt;
import pl.lodz.p.it.tks.domainmodel.resources.Car;
import pl.lodz.p.it.tks.domainmodel.resources.Rent;
import pl.lodz.p.it.tks.domainmodel.user.Customer;
import pl.lodz.p.it.tks.repository.CarEntRepository;
import pl.lodz.p.it.tks.repository.RentEntRepository;
import pl.lodz.p.it.tks.repository.UserEntRepository;
import pl.lodz.p.it.tks.repository.exception.RepositoryEntException;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.UUID;

public class RentRepositoryAdapterTests {
    private RentRepositoryAdapter rentRepositoryAdapter;

    private RentEntRepository rentEntRepository;
    private CarEntRepository carEntRepository;
    private UserEntRepository userEntRepository;

    @BeforeMethod
    public void beforeTest() throws IllegalAccessException, NoSuchFieldException, RepositoryEntException {
        rentRepositoryAdapter = new RentRepositoryAdapter();

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
                    .password("zaq1@WSX")
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
        Assert.assertEquals(rentEntRepository.getAll().size(), 1);
        Assert.assertThrows(RepositoryAdapterException.class, () -> rentRepositoryAdapter.add(rent));
        Assert.assertEquals(rentEntRepository.getAll().size(), 1);
    }

    @Test
    public void getRentTest() throws RepositoryAdapterException, RepositoryEntException {
        RentEnt rentEnt = createRentEnt(1, 1);

        rentEntRepository.add(rentEnt);
        Assert.assertEquals(rentEntRepository.getAll().size(), 1);
        Assert.assertNotEquals(rentEnt.getId(), null);

        Rent rent = rentRepositoryAdapter.get(rentEnt.getId());
        Assert.assertEquals(rent.getId(), rentEnt.getId());
        Assert.assertEquals(rent.getCar().getId(), rentEnt.getCar().getId());
        Assert.assertEquals(rent.getCustomer().getId(), rentEnt.getCustomer().getId());
        Assert.assertEquals(rent.getRentStartDate(), rentEnt.getRentStartDate());
        Assert.assertEquals(rent.getPrice(), rentEnt.getPrice());
        Assert.assertThrows(RepositoryAdapterException.class, () -> rentRepositoryAdapter.get(UUID.randomUUID()));
    }

    @Test
    public void getAllRentTest() throws RepositoryEntException {
        int rentsCount = 5;
        for (int i = 0; i < rentsCount; i++) {
            RentEnt rentEnt = createRentEnt(i, i);
            rentEntRepository.add(rentEnt);
        }

        Assert.assertEquals(rentRepositoryAdapter.getAll().size(), rentsCount);
    }

    @Test
    public void updateRentTest() throws RepositoryEntException, RepositoryAdapterException {
        RentEnt rentEnt = createRentEnt(1, 1);

        rentEntRepository.add(rentEnt);
        Assert.assertEquals(rentEntRepository.getAll().size(), 1);
        Assert.assertNotEquals(rentEnt.getId(), null);
        rentEnt.setRentStartDate(LocalDateTime.now().plusDays(2));
        rentEnt.setRentEndDate(LocalDateTime.now().plusDays(10));

        Rent rent = RentConverter.convertEntToDomain(rentEnt);

        rentRepositoryAdapter.update(rent);
        Assert.assertEquals(rentEntRepository.getAll().size(), 1);

        RentEnt getUpdatedRent = rentEntRepository.get(rentEnt.getId());
        Assert.assertEquals(getUpdatedRent.getId(), rentEnt.getId());
        Assert.assertEquals(getUpdatedRent.getCar().getId(), rentEnt.getCar().getId());
        Assert.assertSame(getUpdatedRent.getCar(), rentEnt.getCar());
        Assert.assertEquals(getUpdatedRent.getCustomer().getId(), rentEnt.getCustomer().getId());
        Assert.assertSame(getUpdatedRent.getCustomer(), rentEnt.getCustomer());
        Assert.assertEquals(getUpdatedRent.getRentStartDate(), rentEnt.getRentStartDate());
        Assert.assertEquals(getUpdatedRent.getRentEndDate(), rentEnt.getRentEndDate());
        Assert.assertEquals(getUpdatedRent.getPrice(), rentEnt.getPrice());

        Rent noUpdateRent = createRent(1, 1);
        Assert.assertThrows(RepositoryAdapterException.class, () -> rentRepositoryAdapter.update(noUpdateRent));
    }

    @Test
    public void deleteRentTest() throws RepositoryEntException, RepositoryAdapterException {
        RentEnt rentEnt = createRentEnt(1, 1);

        rentEntRepository.add(rentEnt);
        Assert.assertEquals(rentEntRepository.getAll().size(), 1);
        Assert.assertNotEquals(rentEnt.getId(), null);

        rentRepositoryAdapter.delete(rentEnt.getId());
        Assert.assertEquals(rentEntRepository.getAll().size(), 0);

        Assert.assertThrows(RepositoryAdapterException.class, () -> rentRepositoryAdapter.delete(rentEnt.getId()));
    }
}

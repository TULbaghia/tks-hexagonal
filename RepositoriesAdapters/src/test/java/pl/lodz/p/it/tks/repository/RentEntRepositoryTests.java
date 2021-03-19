package pl.lodz.p.it.tks.repository;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pl.lodz.p.it.tks.data.resources.CarEnt;
import pl.lodz.p.it.tks.data.resources.EconomyCarEnt;
import pl.lodz.p.it.tks.data.resources.RentEnt;
import pl.lodz.p.it.tks.data.user.CustomerEnt;
import pl.lodz.p.it.tks.repository.exception.RepositoryEntException;

import java.time.LocalDateTime;
import java.util.UUID;

public class RentEntRepositoryTests {

    private RentEntRepository rentEntRepository;
    private CarEntRepository carEntRepository;
    private UserEntRepository userEntRepository;

    @BeforeMethod
    public void beforeTest() throws RepositoryEntException {
        rentEntRepository = new RentEntRepository();
        carEntRepository = new CarEntRepository();
        userEntRepository = new UserEntRepository();

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

    private RentEnt createRentEnt(int customerNo, int carNo) throws RepositoryEntException {
        CustomerEnt customerEnt = (CustomerEnt) userEntRepository.get("Klient" + customerNo);
        CarEnt carEnt  = carEntRepository.get("1111111111111111" + carNo);
        return RentEnt.builder()
                .car(carEnt)
                .customer(customerEnt)
                .build();
    }

    @Test
    public void addRentTest() throws RepositoryEntException {
        RentEnt rentEnt = createRentEnt(1, 1);

        rentEntRepository.add(rentEnt);
        Assert.assertEquals(rentEntRepository.getAll().size(), 1);
        Assert.assertThrows(RepositoryEntException.class, () -> rentEntRepository.add(rentEnt));
        Assert.assertEquals(rentEntRepository.getAll().size(), 1);
    }

    @Test
    public void getRentByIdTest() throws RepositoryEntException {
        RentEnt rentEnt = createRentEnt(1, 1);

        rentEntRepository.add(rentEnt);
        Assert.assertEquals(rentEntRepository.getAll().size(), 1);
        Assert.assertNotEquals(rentEnt.getId(), null);

        RentEnt getRent = rentEntRepository.get(rentEnt.getId());
        Assert.assertEquals(getRent.getId(), rentEnt.getId());

        Assert.assertEquals(getRent.getCar().getId(), rentEnt.getCar().getId());
        Assert.assertSame(getRent.getCar(), rentEnt.getCar());

        Assert.assertEquals(getRent.getCustomer().getId(), rentEnt.getCustomer().getId());
        Assert.assertSame(getRent.getCustomer(), rentEnt.getCustomer());

        Assert.assertEquals(getRent.getRentStartDate(), rentEnt.getRentStartDate());
        Assert.assertEquals(getRent.getPrice(), rentEnt.getPrice());

        Assert.assertThrows(RepositoryEntException.class, () -> rentEntRepository.get(UUID.randomUUID()));
    }

    @Test
    public void getAllRentTest() throws RepositoryEntException {
        int rentsCount = 5;
        for (int i = 0; i < rentsCount; i++) {
            RentEnt rentEnt = createRentEnt(i, i);
            rentEntRepository.add(rentEnt);
        }

        Assert.assertEquals(rentEntRepository.getAll().size(), rentsCount);
    }

    @Test
    public void updateRentTest() throws RepositoryEntException {
        RentEnt rentEnt = createRentEnt(1, 1);

        rentEntRepository.add(rentEnt);
        Assert.assertEquals(rentEntRepository.getAll().size(), 1);
        Assert.assertNotEquals(rentEnt.getId(), null);
        rentEnt.setRentStartDate(LocalDateTime.now().plusDays(2));
        rentEnt.setRentEndDate(LocalDateTime.now().plusDays(10));

        rentEntRepository.update(rentEnt);
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

        Assert.assertThrows(RepositoryEntException.class, () -> rentEntRepository.update(new RentEnt()));
    }

    @Test
    public void deleteRentTest() throws RepositoryEntException {
        RentEnt rentEnt = createRentEnt(1, 1);

        rentEntRepository.add(rentEnt);
        Assert.assertEquals(rentEntRepository.getAll().size(), 1);
        Assert.assertNotEquals(rentEnt.getId(), null);

        rentEntRepository.delete(rentEnt.getId());
        Assert.assertEquals(rentEntRepository.getAll().size(), 0);

        Assert.assertThrows(RepositoryEntException.class, () -> rentEntRepository.delete(rentEnt.getId()));
    }
}

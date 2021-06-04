package pl.lodz.p.it.tks.rent.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.lodz.p.it.tks.rent.data.resources.CarEnt;
import pl.lodz.p.it.tks.rent.data.resources.EconomyCarEnt;
import pl.lodz.p.it.tks.rent.data.resources.RentEnt;
import pl.lodz.p.it.tks.rent.data.user.CustomerEnt;
import pl.lodz.p.it.tks.rent.repository.exception.RepositoryEntException;

import java.time.LocalDateTime;
import java.util.UUID;

public class RentEntRepositoryTests {

    private static RentEntRepository rentEntRepository;
    private static CarEntRepository carEntRepository;
    private static UserEntRepository userEntRepository;

    @BeforeEach
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
        Assertions.assertEquals(rentEntRepository.getAll().size(), 1);
//        Assertions.assertThrows(RepositoryEntException.class, () -> rentEntRepository.add(rentEnt));
        Assertions.assertEquals(rentEntRepository.getAll().size(), 1);
    }

    @Test
    public void getRentByIdTest() throws RepositoryEntException {
        RentEnt rentEnt = createRentEnt(1, 1);

        rentEntRepository.add(rentEnt);
        Assertions.assertEquals(rentEntRepository.getAll().size(), 1);
        Assertions.assertNotEquals(rentEnt.getId(), null);

        RentEnt getRent = rentEntRepository.get(rentEnt.getId());
        Assertions.assertEquals(getRent.getId(), rentEnt.getId());

        Assertions.assertEquals(getRent.getCar().getId(), rentEnt.getCar().getId());
        Assertions.assertSame(getRent.getCar(), rentEnt.getCar());

        Assertions.assertEquals(getRent.getCustomer().getId(), rentEnt.getCustomer().getId());
        Assertions.assertSame(getRent.getCustomer(), rentEnt.getCustomer());

        Assertions.assertEquals(getRent.getRentStartDate(), rentEnt.getRentStartDate());
        Assertions.assertEquals(getRent.getPrice(), rentEnt.getPrice());

        Assertions.assertThrows(RepositoryEntException.class, () -> rentEntRepository.get(UUID.randomUUID()));
    }

    @Test
    public void getAllRentTest() throws RepositoryEntException {
        int rentsCount = 5;
        for (int i = 0; i < rentsCount; i++) {
            RentEnt rentEnt = createRentEnt(i, i);
            rentEntRepository.add(rentEnt);
        }

        Assertions.assertEquals(rentEntRepository.getAll().size(), rentsCount);
    }

    @Test
    public void updateRentTest() throws RepositoryEntException {
        RentEnt rentEnt = createRentEnt(1, 1);

        rentEntRepository.add(rentEnt);
        Assertions.assertEquals(rentEntRepository.getAll().size(), 1);
        Assertions.assertNotEquals(rentEnt.getId(), null);
        rentEnt.setRentStartDate(LocalDateTime.now().plusDays(2));
        rentEnt.setRentEndDate(LocalDateTime.now().plusDays(10));

        rentEntRepository.update(rentEnt);
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

        Assertions.assertThrows(RepositoryEntException.class, () -> rentEntRepository.update(new RentEnt()));
    }

    @Test
    public void deleteRentTest() throws RepositoryEntException {
        RentEnt rentEnt = createRentEnt(1, 1);

        rentEntRepository.add(rentEnt);
        Assertions.assertEquals(rentEntRepository.getAll().size(), 1);
        Assertions.assertNotEquals(rentEnt.getId(), null);

        rentEntRepository.delete(rentEnt.getId());
        Assertions.assertEquals(rentEntRepository.getAll().size(), 0);

        Assertions.assertThrows(RepositoryEntException.class, () -> rentEntRepository.delete(rentEnt.getId()));
    }
}

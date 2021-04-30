package pl.lodz.p.it.tks.rent.applicationservices;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pl.lodz.p.it.tks.rent.applicationports.adapters.driven.CustomerRepositoryAdapter;
import pl.lodz.p.it.tks.rent.applicationports.adapters.driven.RentRepositoryAdapter;
import pl.lodz.p.it.tks.rent.applicationports.converters.CarConverter;
import pl.lodz.p.it.tks.rent.applicationports.converters.UserConverter;
import pl.lodz.p.it.tks.rent.applicationports.exception.RepositoryAdapterException;
import pl.lodz.p.it.tks.rent.data.resources.EconomyCarEnt;
import pl.lodz.p.it.tks.rent.data.user.CustomerEnt;
import pl.lodz.p.it.tks.rent.domainmodel.exception.CustomerException;
import pl.lodz.p.it.tks.rent.domainmodel.exception.RentException;
import pl.lodz.p.it.tks.rent.domainmodel.resources.Car;
import pl.lodz.p.it.tks.rent.domainmodel.resources.Rent;
import pl.lodz.p.it.tks.rent.domainmodel.user.Customer;
import pl.lodz.p.it.tks.rent.repository.CarEntRepository;
import pl.lodz.p.it.tks.rent.repository.RentEntRepository;
import pl.lodz.p.it.tks.rent.repository.UserEntRepository;
import pl.lodz.p.it.tks.rent.repository.exception.RepositoryEntException;

import java.time.LocalDateTime;
import java.util.List;

public class RentServiceTest {

    private UserEntRepository userEntRepository;
    private CarEntRepository carEntRepository;
    private CustomerRepositoryAdapter customerAdapter;
    private RentRepositoryAdapter rentRepositoryAdapter;
    private RentService rentService;

    @BeforeMethod
    public void beforeMethod() throws RepositoryEntException {
        userEntRepository = new UserEntRepository();
        carEntRepository = new CarEntRepository();
        rentRepositoryAdapter = new RentRepositoryAdapter(new RentEntRepository(), userEntRepository, carEntRepository);
        customerAdapter = new CustomerRepositoryAdapter(userEntRepository);
        rentService = new RentService(rentRepositoryAdapter, rentRepositoryAdapter, rentRepositoryAdapter, rentRepositoryAdapter, rentRepositoryAdapter, customerAdapter);

        userEntRepository.add(CustomerEnt.builder().login("customer1").firstname("Custo1").lastname("mer1").build());
        userEntRepository.add(CustomerEnt.builder().login("customer2").firstname("Custo2").lastname("mer2").build());
        userEntRepository.add(CustomerEnt.builder().login("customer3").firstname("Custo3").lastname("mer3").build());

        carEntRepository.add(EconomyCarEnt.builder().vin("12345678901234560").basePricePerDay(50).brand("Fiat").doorNumber(3).driverEquipment("BRAK").engineCapacity(999).build());
        carEntRepository.add(EconomyCarEnt.builder().vin("12345678901234561").basePricePerDay(10).brand("Opel").doorNumber(5).driverEquipment("BRAK").engineCapacity(1000).build());
    }

    @Test
    public void addRentTest() throws RepositoryEntException, RepositoryAdapterException {
        Customer user = UserConverter.convertEntToDomain( (CustomerEnt) userEntRepository.get("customer1"));
        Car car = CarConverter.convertEntToDomain( (EconomyCarEnt) carEntRepository.get("12345678901234560"));

        Rent r = rentService.add(Rent.builder().car(car).customer(user).rentStartDate(LocalDateTime.now()).build());

        Rent rent = rentService.get(r.getId());

        Assert.assertEquals(rent.getCar(), car);
        Assert.assertEquals(rent.getCustomer(), user);
    }

    @Test
    public void getAllRentTest() throws RepositoryAdapterException, RepositoryEntException {
        Customer user = UserConverter.convertEntToDomain( (CustomerEnt) userEntRepository.get("customer1"));
        Car car = CarConverter.convertEntToDomain( (EconomyCarEnt) carEntRepository.get("12345678901234560"));

        Rent r = rentService.add(Rent.builder().car(car).customer(user).rentStartDate(LocalDateTime.now()).build());

        List<Rent> rentList = rentService.getAll();
        Assert.assertEquals(rentList.size(), 1);
        Assert.assertEquals(rentList.get(0), r);
    }

    @Test
    public void getByUUIDRentTest() throws RepositoryAdapterException, RepositoryEntException {
        Customer user = UserConverter.convertEntToDomain( (CustomerEnt) userEntRepository.get("customer1"));
        Car car = CarConverter.convertEntToDomain( (EconomyCarEnt) carEntRepository.get("12345678901234560"));

        Rent r = rentService.add(Rent.builder().car(car).customer(user).rentStartDate(LocalDateTime.now()).build());

        Rent rent = rentService.get(r.getId());

        Assert.assertNotNull(rent.getId());
        Assert.assertEquals(rent.getId(), r.getId());
        Assert.assertEquals(rent.getCar(), car);
        Assert.assertEquals(rent.getCustomer(), user);
    }

    @Test
    public void deleteRentTest() throws RepositoryAdapterException, RepositoryEntException {
        Customer user = UserConverter.convertEntToDomain( (CustomerEnt) userEntRepository.get("customer1"));
        Car car = CarConverter.convertEntToDomain( (EconomyCarEnt) carEntRepository.get("12345678901234560"));

        Rent r = rentService.add(Rent.builder().car(car).customer(user).rentStartDate(LocalDateTime.now()).build());

        Rent rent = rentService.get(r.getId());

        rentService.delete(rent.getId());

        Assert.assertThrows(RepositoryAdapterException.class, () -> rentService.get(r.getId()));
    }

    @Test
    public void endRentTest() throws RepositoryAdapterException, RepositoryEntException, CustomerException, RentException {
        Customer user = UserConverter.convertEntToDomain( (CustomerEnt) userEntRepository.get("customer1"));
        Car car = CarConverter.convertEntToDomain( (EconomyCarEnt) carEntRepository.get("12345678901234560"));

        Rent r = rentService.add(Rent.builder().car(car).customer(user).rentStartDate(LocalDateTime.now()).build());

        rentService.endRent(r.getId());
        Rent ended = rentService.get(r.getId());

        Assert.assertNotEquals(ended.getRentEndDate(), r.getRentEndDate());
    }
}

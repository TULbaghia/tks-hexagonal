package pl.lodz.p.it.tks.repository;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pl.lodz.p.it.tks.data.resources.CarEnt;
import pl.lodz.p.it.tks.data.resources.EconomyCarEnt;
import pl.lodz.p.it.tks.repository.exception.RepositoryEntException;

public class CarEntRepositoryTest {

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
    public void getCarByUuidTest() {
        Assert.assertEquals("cos", "cos");
    }

    @Test
    public void getCarByVinTest() {
        Assert.assertEquals("cos", "cos");
    }

    @Test
    public void getAllCarTest() {
        Assert.assertEquals("cos", "cos");
    }

    @Test
    public void deleteCarTest() {
        Assert.assertEquals("cos", "cos");
    }

    @Test
    public void updateCarTest() {
        Assert.assertEquals("cos", "cos");
    }
}

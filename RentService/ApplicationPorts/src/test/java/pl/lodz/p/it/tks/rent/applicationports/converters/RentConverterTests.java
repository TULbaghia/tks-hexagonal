package pl.lodz.p.it.tks.rent.applicationports.converters;

import org.testng.Assert;
import org.testng.annotations.Test;
import pl.lodz.p.it.tks.rent.data.resources.ExclusiveCarEnt;
import pl.lodz.p.it.tks.rent.data.resources.RentEnt;
import pl.lodz.p.it.tks.rent.data.user.CustomerEnt;
import pl.lodz.p.it.tks.rent.domainmodel.resources.ExclusiveCar;
import pl.lodz.p.it.tks.rent.domainmodel.resources.Rent;
import pl.lodz.p.it.tks.rent.domainmodel.user.Customer;

public class RentConverterTests {
    @Test
    public void rentDomainToEntConverterTest() {
        ExclusiveCar exclusiveCar = ExclusiveCar.builder()
                .vin("8233h4234h234g2")
                .engineCapacity(1.5)
                .doorNumber(5)
                .brand("BMW")
                .basePricePerDay(1000d)
                .driverEquipment("Immobilizer")
                .boardPcName("boardPc")
                .build();

        Customer customer = Customer.builder()
                .firstname("Customer")
                .lastname("Kowalski")
                .login("Klient")
                .build();

        Rent rent = Rent.builder()
                .car(exclusiveCar)
                .customer(customer)
                .build();

        CustomerEnt customerEnt = UserConverter.convertDomainToEnt(customer);
        ExclusiveCarEnt exclusiveCarEnt = CarConverter.convertDomainToEnt(exclusiveCar);
        RentEnt rentEnt = RentConverter.convertDomainToEnt(rent, customerEnt, exclusiveCarEnt);

        Assert.assertEquals(rentEnt.getId(), rent.getId());
        Assert.assertEquals(rentEnt.getCar().getId(), rent.getCar().getId());
        Assert.assertSame(rentEnt.getCar(), exclusiveCarEnt);
        Assert.assertEquals(rentEnt.getCustomer().getId(), rent.getCustomer().getId());
        Assert.assertSame(rentEnt.getCustomer(), customerEnt);
        Assert.assertEquals(rentEnt.getRentStartDate(), rent.getRentStartDate());
        Assert.assertEquals(rentEnt.getPrice(), rent.getPrice());
    }

    @Test
    public void rentEntToDomainConverterTest() {
        ExclusiveCarEnt exclusiveCarEnt = ExclusiveCarEnt.builder()
                .vin("8233h4234h234g2")
                .engineCapacity(1.5)
                .doorNumber(5)
                .brand("BMW")
                .basePricePerDay(1000d)
                .driverEquipment("Immobilizer")
                .boardPcName("boardPc")
                .build();

        CustomerEnt customerEnt = CustomerEnt.builder()
                .firstname("Customer")
                .lastname("Kowalski")
                .login("Klient")
                .build();

        RentEnt rentEnt = RentEnt.builder()
                .car(exclusiveCarEnt)
                .customer(customerEnt)
                .build();

        Rent rent = RentConverter.convertEntToDomain(rentEnt);

        Assert.assertEquals(rent.getId(), rentEnt.getId());
        Assert.assertEquals(rent.getCar().getId(), rentEnt.getCar().getId());
        Assert.assertEquals(rent.getCustomer().getId(), rentEnt.getCustomer().getId());
        Assert.assertEquals(rent.getRentStartDate(), rentEnt.getRentStartDate());
        Assert.assertEquals(rent.getPrice(), rentEnt.getPrice());
    }
}

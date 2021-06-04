package pl.lodz.p.it.tks.rent.applicationports.converters;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
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

        Assertions.assertEquals(rentEnt.getId(), rent.getId());
        Assertions.assertEquals(rentEnt.getCar().getId(), rent.getCar().getId());
        Assertions.assertSame(rentEnt.getCar(), exclusiveCarEnt);
        Assertions.assertEquals(rentEnt.getCustomer().getId(), rent.getCustomer().getId());
        Assertions.assertSame(rentEnt.getCustomer(), customerEnt);
        Assertions.assertEquals(rentEnt.getRentStartDate(), rent.getRentStartDate());
        Assertions.assertEquals(rentEnt.getPrice(), rent.getPrice());
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

        Assertions.assertEquals(rent.getId(), rentEnt.getId());
        Assertions.assertEquals(rent.getCar().getId(), rentEnt.getCar().getId());
        Assertions.assertEquals(rent.getCustomer().getId(), rentEnt.getCustomer().getId());
        Assertions.assertEquals(rent.getRentStartDate(), rentEnt.getRentStartDate());
        Assertions.assertEquals(rent.getPrice(), rentEnt.getPrice());
    }
}

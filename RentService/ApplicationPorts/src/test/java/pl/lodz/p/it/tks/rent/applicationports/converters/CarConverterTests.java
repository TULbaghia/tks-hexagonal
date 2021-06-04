package pl.lodz.p.it.tks.rent.applicationports.converters;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.lodz.p.it.tks.rent.data.resources.EconomyCarEnt;
import pl.lodz.p.it.tks.rent.data.resources.ExclusiveCarEnt;
import pl.lodz.p.it.tks.rent.domainmodel.resources.EconomyCar;
import pl.lodz.p.it.tks.rent.domainmodel.resources.ExclusiveCar;

public class CarConverterTests {

    @Test
    public void economyDomainToEntConverterTest() {
        EconomyCar economyCar = EconomyCar.builder()
                .vin("8233h4234h234g2")
                .engineCapacity(1.5)
                .doorNumber(5)
                .brand("BMW")
                .basePricePerDay(1000d)
                .driverEquipment("Immobilizer")
                .build();

        EconomyCarEnt economyCarEnt = CarConverter.convertDomainToEnt(economyCar);

        Assertions.assertEquals(economyCarEnt.getId(), economyCar.getId());
        Assertions.assertEquals(economyCarEnt.getVin(), economyCar.getVin());
        Assertions.assertEquals(economyCarEnt.getEngineCapacity(), economyCar.getEngineCapacity());
        Assertions.assertEquals(economyCarEnt.getDoorNumber(), economyCar.getDoorNumber());
        Assertions.assertEquals(economyCarEnt.getBrand(), economyCar.getBrand());
        Assertions.assertEquals(economyCarEnt.getBasePricePerDay(), economyCar.getBasePricePerDay());
        Assertions.assertEquals(economyCarEnt.getDriverEquipment(), economyCar.getDriverEquipment());
    }

    @Test
    public void exclusiveDomainToEntConverterTest() {
        ExclusiveCar exclusiveCar = ExclusiveCar.builder()
                .vin("8233h4234h234g2")
                .engineCapacity(1.5)
                .doorNumber(5)
                .brand("BMW")
                .basePricePerDay(1000d)
                .driverEquipment("Immobilizer")
                .boardPcName("boardPc")
                .build();

        ExclusiveCarEnt exclusiveCarEnt = CarConverter.convertDomainToEnt(exclusiveCar);

        Assertions.assertEquals(exclusiveCarEnt.getId(), exclusiveCar.getId());
        Assertions.assertEquals(exclusiveCarEnt.getVin(), exclusiveCar.getVin());
        Assertions.assertEquals(exclusiveCarEnt.getEngineCapacity(), exclusiveCar.getEngineCapacity());
        Assertions.assertEquals(exclusiveCarEnt.getDoorNumber(), exclusiveCar.getDoorNumber());
        Assertions.assertEquals(exclusiveCarEnt.getBrand(), exclusiveCar.getBrand());
        Assertions.assertEquals(exclusiveCarEnt.getBasePricePerDay(), exclusiveCar.getBasePricePerDay());
        Assertions.assertEquals(exclusiveCarEnt.getDriverEquipment(), exclusiveCar.getDriverEquipment());
        Assertions.assertEquals(exclusiveCarEnt.getBoardPcName(), exclusiveCar.getBoardPcName());
    }

    @Test
    public void economyEntToDomainConverterTest() {
        EconomyCarEnt economyCarEnt = EconomyCarEnt.builder()
                .vin("8233h4234h234g2")
                .engineCapacity(1.5)
                .doorNumber(5)
                .brand("BMW")
                .basePricePerDay(1000d)
                .driverEquipment("Immobilizer")
                .build();

        EconomyCar economyCar = CarConverter.convertEntToDomain(economyCarEnt);

        Assertions.assertEquals(economyCar.getId(), economyCarEnt.getId());
        Assertions.assertEquals(economyCar.getVin(), economyCarEnt.getVin());
        Assertions.assertEquals(economyCar.getEngineCapacity(), economyCarEnt.getEngineCapacity());
        Assertions.assertEquals(economyCar.getDoorNumber(), economyCarEnt.getDoorNumber());
        Assertions.assertEquals(economyCar.getBrand(), economyCarEnt.getBrand());
        Assertions.assertEquals(economyCar.getBasePricePerDay(), economyCarEnt.getBasePricePerDay());
        Assertions.assertEquals(economyCar.getDriverEquipment(), economyCarEnt.getDriverEquipment());

    }

    @Test
    public void exclusiveEntToDomainConverterTest() {
        ExclusiveCarEnt exclusiveCarEnt = ExclusiveCarEnt.builder()
                .vin("8233h4234h234g2")
                .engineCapacity(1.5)
                .doorNumber(5)
                .brand("BMW")
                .basePricePerDay(1000d)
                .driverEquipment("Immobilizer")
                .boardPcName("boardPc")
                .build();

        ExclusiveCar exclusiveCar = CarConverter.convertEntToDomain(exclusiveCarEnt);

        Assertions.assertEquals(exclusiveCar.getId(), exclusiveCarEnt.getId());
        Assertions.assertEquals(exclusiveCar.getVin(), exclusiveCarEnt.getVin());
        Assertions.assertEquals(exclusiveCar.getEngineCapacity(), exclusiveCarEnt.getEngineCapacity());
        Assertions.assertEquals(exclusiveCar.getDoorNumber(), exclusiveCarEnt.getDoorNumber());
        Assertions.assertEquals(exclusiveCar.getBrand(), exclusiveCarEnt.getBrand());
        Assertions.assertEquals(exclusiveCar.getBasePricePerDay(), exclusiveCarEnt.getBasePricePerDay());
        Assertions.assertEquals(exclusiveCar.getDriverEquipment(), exclusiveCarEnt.getDriverEquipment());
        Assertions.assertEquals(exclusiveCar.getBoardPcName(), exclusiveCarEnt.getBoardPcName());
    }
}

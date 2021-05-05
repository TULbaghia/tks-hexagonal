package pl.lodz.p.it.tks.rent.applicationports.converters;

import org.testng.Assert;
import org.testng.annotations.Test;
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

        Assert.assertEquals(economyCarEnt.getId(), economyCar.getId());
        Assert.assertEquals(economyCarEnt.getVin(), economyCar.getVin());
        Assert.assertEquals(economyCarEnt.getEngineCapacity(), economyCar.getEngineCapacity());
        Assert.assertEquals(economyCarEnt.getDoorNumber(), economyCar.getDoorNumber());
        Assert.assertEquals(economyCarEnt.getBrand(), economyCar.getBrand());
        Assert.assertEquals(economyCarEnt.getBasePricePerDay(), economyCar.getBasePricePerDay());
        Assert.assertEquals(economyCarEnt.getDriverEquipment(), economyCar.getDriverEquipment());
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

        Assert.assertEquals(exclusiveCarEnt.getId(), exclusiveCar.getId());
        Assert.assertEquals(exclusiveCarEnt.getVin(), exclusiveCar.getVin());
        Assert.assertEquals(exclusiveCarEnt.getEngineCapacity(), exclusiveCar.getEngineCapacity());
        Assert.assertEquals(exclusiveCarEnt.getDoorNumber(), exclusiveCar.getDoorNumber());
        Assert.assertEquals(exclusiveCarEnt.getBrand(), exclusiveCar.getBrand());
        Assert.assertEquals(exclusiveCarEnt.getBasePricePerDay(), exclusiveCar.getBasePricePerDay());
        Assert.assertEquals(exclusiveCarEnt.getDriverEquipment(), exclusiveCar.getDriverEquipment());
        Assert.assertEquals(exclusiveCarEnt.getBoardPcName(), exclusiveCar.getBoardPcName());
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

        Assert.assertEquals(economyCar.getId(), economyCarEnt.getId());
        Assert.assertEquals(economyCar.getVin(), economyCarEnt.getVin());
        Assert.assertEquals(economyCar.getEngineCapacity(), economyCarEnt.getEngineCapacity());
        Assert.assertEquals(economyCar.getDoorNumber(), economyCarEnt.getDoorNumber());
        Assert.assertEquals(economyCar.getBrand(), economyCarEnt.getBrand());
        Assert.assertEquals(economyCar.getBasePricePerDay(), economyCarEnt.getBasePricePerDay());
        Assert.assertEquals(economyCar.getDriverEquipment(), economyCarEnt.getDriverEquipment());

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

        Assert.assertEquals(exclusiveCar.getId(), exclusiveCarEnt.getId());
        Assert.assertEquals(exclusiveCar.getVin(), exclusiveCarEnt.getVin());
        Assert.assertEquals(exclusiveCar.getEngineCapacity(), exclusiveCarEnt.getEngineCapacity());
        Assert.assertEquals(exclusiveCar.getDoorNumber(), exclusiveCarEnt.getDoorNumber());
        Assert.assertEquals(exclusiveCar.getBrand(), exclusiveCarEnt.getBrand());
        Assert.assertEquals(exclusiveCar.getBasePricePerDay(), exclusiveCarEnt.getBasePricePerDay());
        Assert.assertEquals(exclusiveCar.getDriverEquipment(), exclusiveCarEnt.getDriverEquipment());
        Assert.assertEquals(exclusiveCar.getBoardPcName(), exclusiveCarEnt.getBoardPcName());
    }
}

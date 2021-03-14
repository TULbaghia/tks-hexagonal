package pl.lodz.p.it.tks.applicationports.converters;

import pl.lodz.p.it.tks.data.resources.EconomyCarEnt;
import pl.lodz.p.it.tks.data.resources.ExclusiveCarEnt;
import pl.lodz.p.it.tks.domainmodel.resources.EconomyCar;
import pl.lodz.p.it.tks.domainmodel.resources.ExclusiveCar;

public class CarConverter {
    public static EconomyCarEnt convertDomainToEnt(EconomyCar economyCar) {
         return EconomyCarEnt.builder()
                 .id(economyCar.getId())
                 .engineCapacity(economyCar.getEngineCapacity())
                 .vin(economyCar.getVin())
                 .doorNumber(economyCar.getDoorNumber())
                 .brand(economyCar.getBrand())
                 .basePricePerDay(economyCar.getBasePricePerDay())
                 .driverEquipment(economyCar.getDriverEquipment())
                 .build();
    }

    public static ExclusiveCarEnt convertDomainToEnt(ExclusiveCar exclusiveCar) {
        return ExclusiveCarEnt.builder()
                .id(exclusiveCar.getId())
                .engineCapacity(exclusiveCar.getEngineCapacity())
                .vin(exclusiveCar.getVin())
                .doorNumber(exclusiveCar.getDoorNumber())
                .brand(exclusiveCar.getBrand())
                .basePricePerDay(exclusiveCar.getBasePricePerDay())
                .driverEquipment(exclusiveCar.getDriverEquipment())
                .boardPcName(exclusiveCar.getBoardPcName())
                .build();
    }

    public static EconomyCar convertEntToDomain(EconomyCarEnt economyCarEnt) {
        return EconomyCar.builder()
                .id(economyCarEnt.getId())
                .engineCapacity(economyCarEnt.getEngineCapacity())
                .vin(economyCarEnt.getVin())
                .doorNumber(economyCarEnt.getDoorNumber())
                .brand(economyCarEnt.getBrand())
                .basePricePerDay(economyCarEnt.getBasePricePerDay())
                .driverEquipment(economyCarEnt.getDriverEquipment())
                .build();
    }

    public static ExclusiveCar convertEntToDomain(ExclusiveCarEnt exclusiveCarEnt) {
        return ExclusiveCar.builder()
                .id(exclusiveCarEnt.getId())
                .engineCapacity(exclusiveCarEnt.getEngineCapacity())
                .vin(exclusiveCarEnt.getVin())
                .doorNumber(exclusiveCarEnt.getDoorNumber())
                .brand(exclusiveCarEnt.getBrand())
                .basePricePerDay(exclusiveCarEnt.getBasePricePerDay())
                .driverEquipment(exclusiveCarEnt.getDriverEquipment())
                .boardPcName(exclusiveCarEnt.getBoardPcName())
                .build();
    }
}

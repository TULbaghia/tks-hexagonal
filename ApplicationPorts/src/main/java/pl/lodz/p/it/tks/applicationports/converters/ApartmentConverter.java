package pl.lodz.p.it.tks.applicationports.converters;

import pl.lodz.p.it.tks.domainmodel.resource.FiveStarApartment;
import pl.lodz.p.it.tks.domainmodel.resource.ThreeStarApartment;
import pl.lodz.p.it.tks.data.resource.FiveStarApartmentEnt;
import pl.lodz.p.it.tks.data.resource.ThreeStarApartmentEnt;

public class ApartmentConverter {
    public static FiveStarApartmentEnt convertDomainToEnt(FiveStarApartment fiveStarApartment) {
         return FiveStarApartmentEnt.builder()
                 .id(fiveStarApartment.getId())
                 .basePricePerDay(fiveStarApartment.getBasePricePerDay())
                 .doorNumber(fiveStarApartment.getDoorNumber())
                 .howManyBeds(fiveStarApartment.getHowManyBeds())
                 .bonus(fiveStarApartment.getBonus())
                 .pcName(fiveStarApartment.getPcName())
                 .build();
    }

    public static ThreeStarApartmentEnt convertDomainToEnt(ThreeStarApartment threeStarApartment) {
        return ThreeStarApartmentEnt.builder()
                .id(threeStarApartment.getId())
                .basePricePerDay(threeStarApartment.getBasePricePerDay())
                .doorNumber(threeStarApartment.getDoorNumber())
                .howManyBeds(threeStarApartment.getHowManyBeds())
                .bonus(threeStarApartment.getBonus())
                .build();
    }

    public static FiveStarApartment convertEntToDomain(FiveStarApartmentEnt fiveStarApartmentEnt) {
        return FiveStarApartment.builder()
                .id(fiveStarApartmentEnt.getId())
                .basePricePerDay(fiveStarApartmentEnt.getBasePricePerDay())
                .doorNumber(fiveStarApartmentEnt.getDoorNumber())
                .howManyBeds(fiveStarApartmentEnt.getHowManyBeds())
                .bonus(fiveStarApartmentEnt.getBonus())
                .pcName(fiveStarApartmentEnt.getPcName())
                .build();
    }

    public static ThreeStarApartment convertEntToDomain(ThreeStarApartmentEnt threeStarApartmentEnt) {
        return ThreeStarApartment.builder()
                .id(threeStarApartmentEnt.getId())
                .basePricePerDay(threeStarApartmentEnt.getBasePricePerDay())
                .doorNumber(threeStarApartmentEnt.getDoorNumber())
                .howManyBeds(threeStarApartmentEnt.getHowManyBeds())
                .bonus(threeStarApartmentEnt.getBonus())
                .build();
    }
}

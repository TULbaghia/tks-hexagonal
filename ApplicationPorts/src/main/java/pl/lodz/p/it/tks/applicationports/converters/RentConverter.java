package pl.lodz.p.it.tks.applicationports.converters;

import pl.lodz.p.it.tks.applicationports.exception.RepositoryAdapterException;
import pl.lodz.p.it.tks.applicationports.ui.EconomyCarUseCase;
import pl.lodz.p.it.tks.data.resources.CarEnt;
import pl.lodz.p.it.tks.data.resources.EconomyCarEnt;
import pl.lodz.p.it.tks.data.resources.ExclusiveCarEnt;
import pl.lodz.p.it.tks.data.resources.RentEnt;
import pl.lodz.p.it.tks.domainmodel.resources.Car;
import pl.lodz.p.it.tks.domainmodel.resources.EconomyCar;
import pl.lodz.p.it.tks.domainmodel.resources.ExclusiveCar;
import pl.lodz.p.it.tks.domainmodel.resources.Rent;
import pl.lodz.p.it.tks.repository.CarEntRepository;
import pl.lodz.p.it.tks.repository.exception.RepositoryEntException;

import javax.inject.Inject;
import java.util.stream.Stream;

public class RentConverter {

    private static Car convertCar(CarEnt carEnt) {
        if (carEnt instanceof ExclusiveCarEnt) {
            return CarConverter.convertEntToDomain((ExclusiveCarEnt) carEnt);
        } else {
            return CarConverter.convertEntToDomain((EconomyCarEnt) carEnt);
        }
    }

    private static CarEnt convertCarEnt(Car car) {
        if (car instanceof ExclusiveCar) {
            return CarConverter.convertDomainToEnt((ExclusiveCar) car);
        } else {
            return CarConverter.convertDomainToEnt((EconomyCar) car);
        }
    }

    public static RentEnt convertDomainToEnt(Rent rent) {
        return RentEnt.builder()
                .id(rent.getId())
                .customer(UserConverter.convertDomainToEnt(rent.getCustomer()))
                .car(convertCarEnt(rent.getCar()))
                .rentStartDate(rent.getRentStartDate())
                .rentEndDate(rent.getRentEndDate())
                .price(rent.getPrice())
                .build();
    }

    public static Rent convertEntToDomain(RentEnt rentEnt) {
        return Rent.builder()
                .id(rentEnt.getId())
                .customer(UserConverter.convertEntToDomain(rentEnt.getCustomer()))
                .car(convertCar(rentEnt.getCar()))
                .rentStartDate(rentEnt.getRentStartDate())
                .rentEndDate(rentEnt.getRentEndDate())
                .price(rentEnt.getPrice())
                .build();
    }
}

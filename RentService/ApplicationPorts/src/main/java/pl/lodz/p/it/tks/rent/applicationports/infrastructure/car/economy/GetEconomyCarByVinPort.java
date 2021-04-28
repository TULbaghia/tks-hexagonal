package pl.lodz.p.it.tks.rent.applicationports.infrastructure.car.economy;

import pl.lodz.p.it.tks.rent.applicationports.exception.RepositoryAdapterException;
import pl.lodz.p.it.tks.rent.domainmodel.resources.EconomyCar;

public interface GetEconomyCarByVinPort {
    EconomyCar getEconomyCarByVin(String vin) throws RepositoryAdapterException;
}

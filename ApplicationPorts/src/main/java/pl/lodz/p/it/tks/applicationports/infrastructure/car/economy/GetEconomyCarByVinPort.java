package pl.lodz.p.it.tks.applicationports.infrastructure.car.economy;

import pl.lodz.p.it.tks.applicationports.exception.RepositoryAdapterException;
import pl.lodz.p.it.tks.domainmodel.resources.EconomyCar;

public interface GetEconomyCarByVinPort {
    EconomyCar getEconomyCarByVin(String vin) throws RepositoryAdapterException;
}

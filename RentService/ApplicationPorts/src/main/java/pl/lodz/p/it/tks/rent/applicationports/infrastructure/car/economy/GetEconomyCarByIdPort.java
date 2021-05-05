package pl.lodz.p.it.tks.rent.applicationports.infrastructure.car.economy;


import pl.lodz.p.it.tks.rent.applicationports.exception.RepositoryAdapterException;
import pl.lodz.p.it.tks.rent.domainmodel.resources.EconomyCar;
import pl.lodz.p.it.tks.rent.repository.exception.RepositoryEntException;

import java.util.UUID;

public interface GetEconomyCarByIdPort {
    EconomyCar get(UUID id) throws RepositoryEntException, RepositoryAdapterException;
}

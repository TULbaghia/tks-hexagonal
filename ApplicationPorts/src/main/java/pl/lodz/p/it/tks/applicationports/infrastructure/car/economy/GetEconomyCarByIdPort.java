package pl.lodz.p.it.tks.applicationports.infrastructure.car.economy;


import pl.lodz.p.it.tks.applicationports.exception.RepositoryAdapterException;
import pl.lodz.p.it.tks.domainmodel.resources.EconomyCar;
import pl.lodz.p.it.tks.repository.exception.RepositoryEntException;

import java.util.UUID;

public interface GetEconomyCarByIdPort {
    EconomyCar get(UUID id) throws RepositoryEntException, RepositoryAdapterException;
}

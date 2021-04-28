package pl.lodz.p.it.tks.rent.applicationports.infrastructure.car.exclusive;


import pl.lodz.p.it.tks.rent.applicationports.exception.RepositoryAdapterException;
import pl.lodz.p.it.tks.rent.domainmodel.resources.ExclusiveCar;
import pl.lodz.p.it.tks.rent.repository.exception.RepositoryEntException;

import java.util.UUID;

public interface GetExclusiveCarByIdPort {
    ExclusiveCar get(UUID id) throws RepositoryEntException, RepositoryAdapterException;
}

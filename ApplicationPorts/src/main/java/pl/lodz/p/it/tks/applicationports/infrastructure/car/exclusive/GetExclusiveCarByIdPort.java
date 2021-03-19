package pl.lodz.p.it.tks.applicationports.infrastructure.car.exclusive;


import pl.lodz.p.it.tks.applicationports.exception.RepositoryAdapterException;
import pl.lodz.p.it.tks.domainmodel.resources.ExclusiveCar;
import pl.lodz.p.it.tks.repository.exception.RepositoryEntException;

import java.util.UUID;

public interface GetExclusiveCarByIdPort {
    ExclusiveCar get(UUID id) throws RepositoryEntException, RepositoryAdapterException;
}

package pl.lodz.p.it.tks.applicationports.infrastructure.car.exclusive;


import pl.lodz.p.it.tks.domainmodel.resources.ExclusiveCar;

import java.util.UUID;

public interface GetExclusiveCarByIdPort {
    ExclusiveCar get(UUID id);
}

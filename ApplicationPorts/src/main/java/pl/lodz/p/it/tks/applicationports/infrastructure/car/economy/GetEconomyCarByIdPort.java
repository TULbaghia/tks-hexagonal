package pl.lodz.p.it.tks.applicationports.infrastructure.car.economy;


import pl.lodz.p.it.tks.domainmodel.resources.EconomyCar;

import java.util.UUID;

public interface GetEconomyCarByIdPort {
    EconomyCar get(UUID id);
}

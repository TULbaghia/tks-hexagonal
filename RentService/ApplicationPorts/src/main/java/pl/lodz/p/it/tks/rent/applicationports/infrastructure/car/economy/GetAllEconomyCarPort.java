package pl.lodz.p.it.tks.rent.applicationports.infrastructure.car.economy;

import pl.lodz.p.it.tks.rent.domainmodel.resources.EconomyCar;

import java.util.List;

public interface GetAllEconomyCarPort {
    List<EconomyCar> getAll();
}

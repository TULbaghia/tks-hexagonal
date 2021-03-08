package pl.lodz.p.it.tks.applicationports.infrastructure.car.economy;

import pl.lodz.p.it.tks.domainmodel.resources.EconomyCar;

import java.util.List;

public interface GetAllEconomyCarPort {
    List<EconomyCar> getAll();
}

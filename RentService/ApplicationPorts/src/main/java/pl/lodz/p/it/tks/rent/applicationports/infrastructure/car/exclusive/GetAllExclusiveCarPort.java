package pl.lodz.p.it.tks.rent.applicationports.infrastructure.car.exclusive;

import pl.lodz.p.it.tks.rent.domainmodel.resources.ExclusiveCar;

import java.util.List;

public interface GetAllExclusiveCarPort {
    List<ExclusiveCar> getAll();
}

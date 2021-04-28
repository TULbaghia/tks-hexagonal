package pl.lodz.p.it.tks.rent.applicationports.infrastructure.car.exclusive;

import pl.lodz.p.it.tks.rent.applicationports.exception.RepositoryAdapterException;
import pl.lodz.p.it.tks.rent.domainmodel.resources.ExclusiveCar;

public interface UpdateExclusiveCarPort {
    ExclusiveCar update(ExclusiveCar item) throws RepositoryAdapterException;
}

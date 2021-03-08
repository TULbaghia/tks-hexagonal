package pl.lodz.p.it.tks.applicationports.infrastructure.car.exclusive;

import pl.lodz.p.it.tks.applicationports.exception.RepositoryAdapterException;
import pl.lodz.p.it.tks.domainmodel.resources.ExclusiveCar;

public interface AddExclusiveCarPort {
    void add(ExclusiveCar item) throws RepositoryAdapterException;
}

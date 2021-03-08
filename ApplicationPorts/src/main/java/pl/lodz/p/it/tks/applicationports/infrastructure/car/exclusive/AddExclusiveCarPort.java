package pl.lodz.p.it.tks.applicationports.infrastructure.car.exclusive;

import lombok.NonNull;
import pl.lodz.p.it.tks.applicationports.exception.RepositoryAdapterException;
import pl.lodz.p.it.tks.domainmodel.resources.ExclusiveCar;

public interface AddExclusiveCarPort {
    void add(@NonNull ExclusiveCar item) throws RepositoryAdapterException;
}

package pl.lodz.p.it.tks.applicationports.infrastructure.car.economy;

import lombok.NonNull;
import pl.lodz.p.it.tks.applicationports.exception.RepositoryAdapterException;
import pl.lodz.p.it.tks.domainmodel.resources.EconomyCar;

public interface AddEconomyCarPort {
    void add(EconomyCar item) throws RepositoryAdapterException;
}

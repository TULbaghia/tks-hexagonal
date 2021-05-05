package pl.lodz.p.it.tks.rent.applicationports.infrastructure.rent;

import pl.lodz.p.it.tks.rent.applicationports.exception.RepositoryAdapterException;
import pl.lodz.p.it.tks.rent.domainmodel.resources.Rent;

public interface AddRentPort {
    Rent add(Rent rent) throws RepositoryAdapterException;
}

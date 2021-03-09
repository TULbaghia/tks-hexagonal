package pl.lodz.p.it.tks.applicationports.infrastructure.rent;

import pl.lodz.p.it.tks.applicationports.exception.RepositoryAdapterException;
import pl.lodz.p.it.tks.domainmodel.resources.Rent;

public interface UpdateRentPort {
    Rent update(Rent rent) throws RepositoryAdapterException;
}
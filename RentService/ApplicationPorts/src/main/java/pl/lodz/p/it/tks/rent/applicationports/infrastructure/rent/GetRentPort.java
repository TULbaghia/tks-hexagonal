package pl.lodz.p.it.tks.rent.applicationports.infrastructure.rent;

import pl.lodz.p.it.tks.rent.applicationports.exception.RepositoryAdapterException;
import pl.lodz.p.it.tks.rent.domainmodel.resources.Rent;

import java.util.UUID;

public interface GetRentPort {
    Rent get(UUID uuid) throws RepositoryAdapterException;
}

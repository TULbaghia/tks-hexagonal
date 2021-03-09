package pl.lodz.p.it.tks.applicationports.infrastructure.rent;

import pl.lodz.p.it.tks.applicationports.exception.RepositoryAdapterException;
import pl.lodz.p.it.tks.domainmodel.resources.Rent;

import java.util.UUID;

public interface GetRentPort {
    Rent get(UUID uuid) throws RepositoryAdapterException;
}
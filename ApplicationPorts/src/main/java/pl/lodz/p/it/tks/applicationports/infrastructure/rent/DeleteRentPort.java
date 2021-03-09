package pl.lodz.p.it.tks.applicationports.infrastructure.rent;

import pl.lodz.p.it.tks.applicationports.exception.RepositoryAdapterException;

import java.util.UUID;

public interface DeleteRentPort {
    void delete(UUID id) throws RepositoryAdapterException;
}

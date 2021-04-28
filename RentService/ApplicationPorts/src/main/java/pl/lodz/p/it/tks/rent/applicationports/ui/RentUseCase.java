package pl.lodz.p.it.tks.rent.applicationports.ui;

import pl.lodz.p.it.tks.rent.applicationports.exception.RepositoryAdapterException;
import pl.lodz.p.it.tks.rent.domainmodel.resources.Rent;

import java.util.List;
import java.util.UUID;

public interface RentUseCase {
    Rent add(Rent rent) throws RepositoryAdapterException;

    Rent update(Rent rent) throws RepositoryAdapterException;

    List<Rent> getAll();

    Rent get(UUID uuid) throws RepositoryAdapterException;

    void delete(UUID id) throws RepositoryAdapterException;

    Rent endRent(UUID id) throws RepositoryAdapterException;
}

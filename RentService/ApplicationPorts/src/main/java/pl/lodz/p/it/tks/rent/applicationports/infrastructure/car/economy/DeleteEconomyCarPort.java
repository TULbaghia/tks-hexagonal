package pl.lodz.p.it.tks.rent.applicationports.infrastructure.car.economy;

import pl.lodz.p.it.tks.rent.applicationports.exception.RepositoryAdapterException;

import java.util.UUID;

public interface DeleteEconomyCarPort {
    void delete(UUID id) throws RepositoryAdapterException;
}

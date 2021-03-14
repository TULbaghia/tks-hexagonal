package pl.lodz.p.it.tks.applicationports.infrastructure.car.economy;

import pl.lodz.p.it.tks.applicationports.exception.RepositoryAdapterException;

import java.util.UUID;

public interface DeleteEconomyCarPort {
    void delete(UUID id) throws RepositoryAdapterException;
}

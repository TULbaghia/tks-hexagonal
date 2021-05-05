package pl.lodz.p.it.tks.rent.applicationports.infrastructure.car.exclusive;

import pl.lodz.p.it.tks.rent.applicationports.exception.RepositoryAdapterException;

import java.util.UUID;

public interface DeleteExclusiveCarPort {
    void delete(UUID id) throws RepositoryAdapterException;
}

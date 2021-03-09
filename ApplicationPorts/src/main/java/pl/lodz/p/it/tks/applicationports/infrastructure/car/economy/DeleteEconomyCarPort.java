package pl.lodz.p.it.tks.applicationports.infrastructure.car.economy;

import lombok.NonNull;
import pl.lodz.p.it.tks.repository.exception.RepositoryEntException;

import java.util.UUID;

public interface DeleteEconomyCarPort {
    void delete(UUID id) throws RepositoryEntException;
}

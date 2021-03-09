package pl.lodz.p.it.tks.applicationports.infrastructure.car.exclusive;

import lombok.NonNull;
import pl.lodz.p.it.tks.repository.exception.RepositoryEntException;

import java.util.UUID;

public interface DeleteExclusiveCarPort {
    void delete(UUID id) throws RepositoryEntException;
}

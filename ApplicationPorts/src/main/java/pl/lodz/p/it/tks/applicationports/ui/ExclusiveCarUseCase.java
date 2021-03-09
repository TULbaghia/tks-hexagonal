package pl.lodz.p.it.tks.applicationports.ui;

import lombok.NonNull;
import pl.lodz.p.it.tks.applicationports.exception.RepositoryAdapterException;
import pl.lodz.p.it.tks.domainmodel.resources.ExclusiveCar;
import pl.lodz.p.it.tks.repository.exception.RepositoryEntException;

import java.util.List;
import java.util.UUID;

public interface ExclusiveCarUseCase {
    ExclusiveCar add(ExclusiveCar car) throws RepositoryAdapterException;

    ExclusiveCar get(UUID id) throws RepositoryAdapterException;

    List<ExclusiveCar> getAll();

    ExclusiveCar update(ExclusiveCar car) throws RepositoryAdapterException;

    void delete(UUID id) throws RepositoryEntException;
}

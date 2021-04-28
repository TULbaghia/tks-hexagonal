package pl.lodz.p.it.tks.rent.applicationports.ui;

import pl.lodz.p.it.tks.rent.applicationports.exception.RepositoryAdapterException;
import pl.lodz.p.it.tks.rent.domainmodel.resources.ExclusiveCar;

import java.util.List;
import java.util.UUID;

public interface ExclusiveCarUseCase {
    ExclusiveCar add(ExclusiveCar car) throws RepositoryAdapterException;

    ExclusiveCar get(UUID id) throws RepositoryAdapterException;

    ExclusiveCar get(String vin) throws RepositoryAdapterException;

    List<ExclusiveCar> getAll();

    ExclusiveCar update(ExclusiveCar car) throws RepositoryAdapterException;

    void delete(UUID id) throws RepositoryAdapterException;
}

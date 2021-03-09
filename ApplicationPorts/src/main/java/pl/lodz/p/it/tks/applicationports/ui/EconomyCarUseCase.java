package pl.lodz.p.it.tks.applicationports.ui;

import pl.lodz.p.it.tks.applicationports.exception.RepositoryAdapterException;
import pl.lodz.p.it.tks.domainmodel.resources.EconomyCar;
import pl.lodz.p.it.tks.repository.exception.RepositoryEntException;

import java.util.List;
import java.util.UUID;

public interface EconomyCarUseCase {
    EconomyCar add(EconomyCar car) throws RepositoryAdapterException;

    EconomyCar get(UUID id) throws RepositoryAdapterException;

    List<EconomyCar> getAll();

    EconomyCar update(EconomyCar car) throws RepositoryAdapterException;

    void delete(UUID id) throws RepositoryEntException;
}

package pl.lodz.p.it.tks.rent.applicationports.ui;

import pl.lodz.p.it.tks.rent.applicationports.exception.RepositoryAdapterException;
import pl.lodz.p.it.tks.rent.domainmodel.resources.EconomyCar;

import java.util.List;
import java.util.UUID;

public interface EconomyCarUseCase {
    EconomyCar add(EconomyCar car) throws RepositoryAdapterException;

    EconomyCar get(UUID id) throws RepositoryAdapterException;

    EconomyCar get(String vin) throws RepositoryAdapterException;

    List<EconomyCar> getAll();

    EconomyCar update(EconomyCar car) throws RepositoryAdapterException;

    void delete(UUID id) throws RepositoryAdapterException;
}

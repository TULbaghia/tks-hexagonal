package pl.lodz.p.it.tks.applicationports.ui;

import lombok.NonNull;
import pl.lodz.p.it.tks.applicationports.exception.RepositoryAdapterException;
import pl.lodz.p.it.tks.domainmodel.resources.EconomyCar;

import java.util.List;
import java.util.UUID;

public interface EconomyCarUseCase {
    void add(EconomyCar car) throws RepositoryAdapterException;

    EconomyCar get(UUID id);

    List<EconomyCar> getAll();

    void update(EconomyCar car) throws RepositoryAdapterException;

    void delete(UUID id);
}

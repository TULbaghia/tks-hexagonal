package pl.lodz.p.it.tks.applicationports.ui;

import lombok.NonNull;
import pl.lodz.p.it.tks.applicationports.exception.RepositoryAdapterException;
import pl.lodz.p.it.tks.domainmodel.resources.ExclusiveCar;

import java.util.List;
import java.util.UUID;

public interface ExclusiveCarUseCase {
    void add(@NonNull ExclusiveCar car) throws RepositoryAdapterException;

    ExclusiveCar get(UUID id);

    List<ExclusiveCar> getAll();

    void update(@NonNull ExclusiveCar car) throws RepositoryAdapterException;

    void delete(@NonNull UUID id);
}

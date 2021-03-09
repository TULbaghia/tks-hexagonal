package pl.lodz.p.it.tks.applicationservices;

import pl.lodz.p.it.tks.applicationports.exception.RepositoryAdapterException;
import pl.lodz.p.it.tks.applicationports.infrastructure.car.exclusive.*;
import pl.lodz.p.it.tks.applicationports.ui.ExclusiveCarUseCase;
import pl.lodz.p.it.tks.domainmodel.resources.ExclusiveCar;
import pl.lodz.p.it.tks.repository.exception.RepositoryEntException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class ExclusiveCarService implements ExclusiveCarUseCase {
    @Inject
    private AddExclusiveCarPort addExclusiveCarPort;
    @Inject
    private GetExclusiveCarByIdPort getExclusiveCarByIdPort;
    @Inject
    private GetAllExclusiveCarPort getAllExclusiveCarPort;
    @Inject
    private UpdateExclusiveCarPort updateExclusiveCarPort;
    @Inject
    private DeleteExclusiveCarPort deleteExclusiveCarPort;

    @Override
    public ExclusiveCar add(ExclusiveCar car) throws RepositoryAdapterException {
        return addExclusiveCarPort.add(car);
    }

    @Override
    public ExclusiveCar get(UUID id) throws RepositoryAdapterException {
        try {
            return getExclusiveCarByIdPort.get(id);
        } catch (RepositoryEntException e) {
            throw new RepositoryAdapterException(e);
        }
    }

    @Override
    public List<ExclusiveCar> getAll() {
        return getAllExclusiveCarPort.getAll();
    }

    @Override
    public ExclusiveCar update(ExclusiveCar car) throws RepositoryAdapterException {
        return updateExclusiveCarPort.update(car);
    }

    @Override
    public void delete(UUID id) throws RepositoryEntException {
        deleteExclusiveCarPort.delete(id);
    }
}

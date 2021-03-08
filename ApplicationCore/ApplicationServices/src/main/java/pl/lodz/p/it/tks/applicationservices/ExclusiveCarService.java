package pl.lodz.p.it.tks.applicationservices;

import lombok.NonNull;
import pl.lodz.p.it.tks.applicationports.exception.RepositoryAdapterException;
import pl.lodz.p.it.tks.applicationports.infrastructure.car.exclusive.*;
import pl.lodz.p.it.tks.applicationports.ui.ExclusiveCarUseCase;
import pl.lodz.p.it.tks.domainmodel.resources.ExclusiveCar;

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
    public void add(ExclusiveCar car) throws RepositoryAdapterException {
        addExclusiveCarPort.add(car);
    }

    @Override
    public ExclusiveCar get(UUID id) {
        return getExclusiveCarByIdPort.get(id);
    }

    @Override
    public List<ExclusiveCar> getAll() {
        return getAllExclusiveCarPort.getAll();
    }

    @Override
    public void update(ExclusiveCar car) throws RepositoryAdapterException {
        updateExclusiveCarPort.update(car);
    }

    @Override
    public void delete(UUID id) {
        deleteExclusiveCarPort.delete(id);
    }
}

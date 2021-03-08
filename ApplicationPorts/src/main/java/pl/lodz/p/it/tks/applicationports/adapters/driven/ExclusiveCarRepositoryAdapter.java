package pl.lodz.p.it.tks.applicationports.adapters.driven;

import lombok.NonNull;
import pl.lodz.p.it.tks.applicationports.exception.RepositoryAdapterException;
import pl.lodz.p.it.tks.applicationports.converters.CarConverter;
import pl.lodz.p.it.tks.applicationports.infrastructure.car.exclusive.*;
import pl.lodz.p.it.tks.data.resources.ExclusiveCarEnt;
import pl.lodz.p.it.tks.domainmodel.resources.ExclusiveCar;
import pl.lodz.p.it.tks.repository.CarEntRepository;
import pl.lodz.p.it.tks.repository.exception.RepositoryEntException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@ApplicationScoped
public class ExclusiveCarRepositoryAdapter implements AddExclusiveCarPort, GetExclusiveCarByIdPort,
        GetAllExclusiveCarPort, UpdateExclusiveCarPort, DeleteExclusiveCarPort {

    @Inject
    private CarEntRepository carEntRepository;

    @Override
    public void add(@NonNull ExclusiveCar exclusiveCar) throws RepositoryAdapterException {
        ExclusiveCarEnt exclusiveCarEnt = CarConverter.convertDomainToEnt(exclusiveCar);
        try {
            carEntRepository.add(exclusiveCarEnt);
        } catch (RepositoryEntException e) {
            throw new RepositoryAdapterException(e);
        }
    }

    @Override
    public void delete(@NonNull UUID id) {
        carEntRepository.delete(id);
    }

    @Override
    public List<ExclusiveCar> getAll() {
        return carEntRepository.getAll().stream()
                .filter(x -> x instanceof ExclusiveCarEnt)
                .map(x -> CarConverter.convertEntToDomain((ExclusiveCarEnt) x))
                .collect(Collectors.toList());
    }

    @Override
    public ExclusiveCar get(UUID id) {
        return CarConverter.convertEntToDomain((ExclusiveCarEnt) carEntRepository.get(id));
    }

    @Override
    public void update(@NonNull ExclusiveCar exclusiveCar) throws RepositoryAdapterException {
        ExclusiveCarEnt exclusiveCarEnt = CarConverter.convertDomainToEnt(exclusiveCar);
        try {
            carEntRepository.update(exclusiveCarEnt);
        } catch (RepositoryEntException | InvocationTargetException | IllegalAccessException e) {
            throw new RepositoryAdapterException(e);
        }
    }
}

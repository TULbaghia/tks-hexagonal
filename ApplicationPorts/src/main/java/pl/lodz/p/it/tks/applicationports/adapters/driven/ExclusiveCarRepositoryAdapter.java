package pl.lodz.p.it.tks.applicationports.adapters.driven;

import pl.lodz.p.it.tks.applicationports.converters.CarConverter;
import pl.lodz.p.it.tks.applicationports.exception.RepositoryAdapterException;
import pl.lodz.p.it.tks.applicationports.infrastructure.car.exclusive.*;
import pl.lodz.p.it.tks.data.resources.ExclusiveCarEnt;
import pl.lodz.p.it.tks.domainmodel.resources.ExclusiveCar;
import pl.lodz.p.it.tks.repository.CarEntRepository;
import pl.lodz.p.it.tks.repository.exception.RepositoryEntException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@ApplicationScoped
public class ExclusiveCarRepositoryAdapter implements AddExclusiveCarPort, GetExclusiveCarByIdPort,
        GetAllExclusiveCarPort, UpdateExclusiveCarPort, DeleteExclusiveCarPort {

    @Inject
    private CarEntRepository carEntRepository;

    @Override
    public ExclusiveCar add(ExclusiveCar exclusiveCar) throws RepositoryAdapterException {
        ExclusiveCarEnt exclusiveCarEnt = CarConverter.convertDomainToEnt(exclusiveCar);
        try {
            return CarConverter.convertEntToDomain((ExclusiveCarEnt) carEntRepository.add(exclusiveCarEnt));
        } catch (RepositoryEntException e) {
            throw new RepositoryAdapterException(e);
        }
    }

    @Override
    public void delete(UUID id) throws RepositoryEntException {
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
    public ExclusiveCar get(UUID id) throws RepositoryEntException {
        return CarConverter.convertEntToDomain((ExclusiveCarEnt) carEntRepository.get(id));
    }

    @Override
    public ExclusiveCar update(ExclusiveCar exclusiveCar) throws RepositoryAdapterException {
        ExclusiveCarEnt exclusiveCarEnt = CarConverter.convertDomainToEnt(exclusiveCar);
        try {
            return CarConverter.convertEntToDomain((ExclusiveCarEnt) carEntRepository.update(exclusiveCarEnt));
        } catch (RepositoryEntException e) {
            throw new RepositoryAdapterException(e);
        }
    }
}

package pl.lodz.p.it.tks.rent.applicationports.adapters.driven;

import pl.lodz.p.it.tks.rent.applicationports.converters.CarConverter;
import pl.lodz.p.it.tks.rent.applicationports.exception.RepositoryAdapterException;
import pl.lodz.p.it.tks.rent.applicationports.infrastructure.car.exclusive.*;
import pl.lodz.p.it.tks.rent.data.resources.ExclusiveCarEnt;
import pl.lodz.p.it.tks.rent.domainmodel.resources.ExclusiveCar;
import pl.lodz.p.it.tks.rent.applicationports.infrastructure.car.exclusive.*;
import pl.lodz.p.it.tks.rent.applicationports.infrastructure.car.exclusive.*;
import pl.lodz.p.it.tks.rent.repository.CarEntRepository;
import pl.lodz.p.it.tks.rent.repository.exception.RepositoryEntException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@ApplicationScoped
public class ExclusiveCarRepositoryAdapter implements AddExclusiveCarPort, GetExclusiveCarByIdPort,
        GetAllExclusiveCarPort, UpdateExclusiveCarPort, DeleteExclusiveCarPort, GetExclusiveCarByVinPort {

    private final CarEntRepository carEntRepository;

    @Inject
    public ExclusiveCarRepositoryAdapter(CarEntRepository carEntRepository) {
        this.carEntRepository = carEntRepository;
    }

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
    public void delete(UUID id) throws RepositoryAdapterException {
        try {
            carEntRepository.delete(id);
        } catch (RepositoryEntException e) {
            throw new RepositoryAdapterException(e);
        }
    }

    @Override
    public List<ExclusiveCar> getAll() {
        return carEntRepository.getAll().stream()
                .filter(x -> x instanceof ExclusiveCarEnt)
                .map(x -> CarConverter.convertEntToDomain((ExclusiveCarEnt) x))
                .collect(Collectors.toList());
    }

    @Override
    public ExclusiveCar get(UUID id) throws RepositoryAdapterException {
        try {
            return CarConverter.convertEntToDomain((ExclusiveCarEnt) carEntRepository.get(id));
        } catch (RepositoryEntException e) {
            throw new RepositoryAdapterException(e);
        }
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

    @Override
    public ExclusiveCar getExclusiveCarByVin(String vin) throws RepositoryAdapterException {
        try {
            return CarConverter.convertEntToDomain((ExclusiveCarEnt) carEntRepository.get(vin));
        } catch (RepositoryEntException e) {
            throw new RepositoryAdapterException(e);
        }
    }
}

package pl.lodz.p.it.tks.rent.applicationports.adapters.driven;

import pl.lodz.p.it.tks.rent.applicationports.converters.CarConverter;
import pl.lodz.p.it.tks.rent.applicationports.exception.RepositoryAdapterException;
import pl.lodz.p.it.tks.rent.applicationports.infrastructure.car.economy.*;
import pl.lodz.p.it.tks.rent.data.resources.EconomyCarEnt;
import pl.lodz.p.it.tks.rent.domainmodel.resources.EconomyCar;
import pl.lodz.p.it.tks.rent.applicationports.infrastructure.car.economy.*;
import pl.lodz.p.it.tks.rent.applicationports.infrastructure.car.economy.*;
import pl.lodz.p.it.tks.rent.repository.CarEntRepository;
import pl.lodz.p.it.tks.rent.repository.exception.RepositoryEntException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@ApplicationScoped
public class EconomyCarRepositoryAdapter implements AddEconomyCarPort, GetEconomyCarByIdPort,
        GetAllEconomyCarPort, UpdateEconomyCarPort, DeleteEconomyCarPort, GetEconomyCarByVinPort {

    private final CarEntRepository carEntRepository;

    @Inject
    public EconomyCarRepositoryAdapter(CarEntRepository carEntRepository) {
        this.carEntRepository = carEntRepository;
    }


    @Override
    public EconomyCar add(EconomyCar economyCar) throws RepositoryAdapterException {
        EconomyCarEnt economyCarEnt = CarConverter.convertDomainToEnt(economyCar);
        try {
            return CarConverter.convertEntToDomain((EconomyCarEnt) carEntRepository.add(economyCarEnt));
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
    public List<EconomyCar> getAll() {
        return carEntRepository.getAll().stream()
                .filter(x -> x instanceof EconomyCarEnt)
                .map(x -> CarConverter.convertEntToDomain((EconomyCarEnt) x))
                .collect(Collectors.toList());
    }

    @Override
    public EconomyCar get(UUID id) throws RepositoryAdapterException {
        try {
            return CarConverter.convertEntToDomain((EconomyCarEnt) carEntRepository.get(id));
        } catch (RepositoryEntException e) {
            throw new RepositoryAdapterException(e);
        }
    }

    @Override
    public EconomyCar update(EconomyCar economyCar) throws RepositoryAdapterException {
        EconomyCarEnt economyCarEnt = CarConverter.convertDomainToEnt(economyCar);
        try {
            return CarConverter.convertEntToDomain((EconomyCarEnt) carEntRepository.update(economyCarEnt));
        } catch (RepositoryEntException e) {
            throw new RepositoryAdapterException(e);
        }
    }

    @Override
    public EconomyCar getEconomyCarByVin(String vin) throws RepositoryAdapterException {
        try {
            return CarConverter.convertEntToDomain((EconomyCarEnt) carEntRepository.get(vin));
        } catch (RepositoryEntException e) {
            throw new RepositoryAdapterException(e);
        }
    }
}

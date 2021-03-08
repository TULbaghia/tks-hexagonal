package pl.lodz.p.it.tks.applicationports.adapters.driven;

import pl.lodz.p.it.tks.applicationports.exception.RepositoryAdapterException;
import pl.lodz.p.it.tks.applicationports.converters.CarConverter;
import pl.lodz.p.it.tks.applicationports.infrastructure.car.economy.AddEconomyCarPort;
import pl.lodz.p.it.tks.applicationports.infrastructure.car.economy.DeleteEconomyCarPort;
import pl.lodz.p.it.tks.applicationports.infrastructure.car.economy.GetEconomyCarByIdPort;
import pl.lodz.p.it.tks.applicationports.infrastructure.car.economy.UpdateEconomyCarPort;
import pl.lodz.p.it.tks.applicationports.infrastructure.car.economy.GetAllEconomyCarPort;
import pl.lodz.p.it.tks.data.resources.EconomyCarEnt;
import pl.lodz.p.it.tks.domainmodel.resources.EconomyCar;
import pl.lodz.p.it.tks.repository.CarEntRepository;
import pl.lodz.p.it.tks.repository.exception.RepositoryEntException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@ApplicationScoped
public class EconomyCarRepositoryAdapter implements AddEconomyCarPort, GetEconomyCarByIdPort,
        GetAllEconomyCarPort, UpdateEconomyCarPort, DeleteEconomyCarPort {

    @Inject
    private CarEntRepository carEntRepository;

    @Override
    public void add(EconomyCar economyCar) throws RepositoryAdapterException {
        EconomyCarEnt economyCarEnt = CarConverter.convertDomainToEnt(economyCar);
        try {
            carEntRepository.add(economyCarEnt);
        } catch (RepositoryEntException e) {
            throw new RepositoryAdapterException(e);
        }
    }

    @Override
    public void delete(UUID id) {
        carEntRepository.delete(id);
    }

    @Override
    public List<EconomyCar> getAll() {
        return carEntRepository.getAll().stream()
                .filter(x -> x instanceof EconomyCarEnt)
                .map(x -> CarConverter.convertEntToDomain((EconomyCarEnt) x))
                .collect(Collectors.toList());
    }

    @Override
    public EconomyCar get(UUID id) {
        return CarConverter.convertEntToDomain((EconomyCarEnt) carEntRepository.get(id));
    }

    @Override
    public void update(EconomyCar economyCar) throws RepositoryAdapterException {
        EconomyCarEnt economyCarEnt = CarConverter.convertDomainToEnt(economyCar);
        try {
            carEntRepository.update(economyCarEnt);
        } catch (RepositoryEntException | InvocationTargetException | IllegalAccessException e) {
            throw new RepositoryAdapterException(e);
        }
    }
}

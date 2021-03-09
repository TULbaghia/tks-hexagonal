package pl.lodz.p.it.tks.applicationservices;

import pl.lodz.p.it.tks.applicationports.exception.RepositoryAdapterException;
import pl.lodz.p.it.tks.applicationports.infrastructure.car.economy.*;
import pl.lodz.p.it.tks.applicationports.ui.EconomyCarUseCase;
import pl.lodz.p.it.tks.domainmodel.resources.EconomyCar;
import pl.lodz.p.it.tks.repository.exception.RepositoryEntException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class EconomyCarService implements EconomyCarUseCase {
    @Inject
    private AddEconomyCarPort addEconomyCarPort;
    @Inject
    private GetEconomyCarByIdPort getEconomyCarByIdPort;
    @Inject
    private GetAllEconomyCarPort getAllEconomyCarPort;
    @Inject
    private UpdateEconomyCarPort updateEconomyCarPort;
    @Inject
    private DeleteEconomyCarPort deleteEconomyCarPort;

    @Override
    public EconomyCar add(EconomyCar car) throws RepositoryAdapterException {
        return addEconomyCarPort.add(car);
    }

    @Override
    public EconomyCar get(UUID id) throws RepositoryEntException {
        return getEconomyCarByIdPort.get(id);
    }

    @Override
    public List<EconomyCar> getAll() {
        return getAllEconomyCarPort.getAll();
    }

    @Override
    public EconomyCar update(EconomyCar car) throws RepositoryAdapterException {
        return updateEconomyCarPort.update(car);
    }

    @Override
    public void delete(UUID id) throws RepositoryEntException {
        deleteEconomyCarPort.delete(id);
    }
}

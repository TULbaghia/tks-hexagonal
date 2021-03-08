package pl.lodz.p.it.tks.applicationservices;

import lombok.NonNull;
import pl.lodz.p.it.tks.applicationports.exception.RepositoryAdapterException;
import pl.lodz.p.it.tks.applicationports.infrastructure.car.economy.*;
import pl.lodz.p.it.tks.applicationports.ui.EconomyCarUseCase;
import pl.lodz.p.it.tks.domainmodel.resources.EconomyCar;

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
    public void add(EconomyCar car) throws RepositoryAdapterException {
            addEconomyCarPort.add(car);
    }

    @Override
    public EconomyCar get(UUID id) {
        return getEconomyCarByIdPort.get(id);
    }

    @Override
    public List<EconomyCar> getAll() {
        return getAllEconomyCarPort.getAll();
    }

    @Override
    public void update(EconomyCar car) throws RepositoryAdapterException {
            updateEconomyCarPort.update(car);
    }

    @Override
    public void delete(UUID id) {
        deleteEconomyCarPort.delete(id);
    }
}

package pl.lodz.p.it.tks.rent.applicationservices;

import pl.lodz.p.it.tks.rent.applicationports.exception.RepositoryAdapterException;
import pl.lodz.p.it.tks.rent.applicationports.infrastructure.car.economy.*;
import pl.lodz.p.it.tks.rent.applicationports.infrastructure.rent.GetAllRentPort;
import pl.lodz.p.it.tks.rent.applicationports.infrastructure.rent.UpdateRentPort;
import pl.lodz.p.it.tks.rent.applicationports.ui.EconomyCarUseCase;
import pl.lodz.p.it.tks.rent.domainmodel.resources.EconomyCar;
import pl.lodz.p.it.tks.rent.repository.exception.RepositoryEntException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class EconomyCarService implements EconomyCarUseCase {
    private final AddEconomyCarPort addEconomyCarPort;
    private final GetEconomyCarByIdPort getEconomyCarByIdPort;
    private final GetAllEconomyCarPort getAllEconomyCarPort;
    private final UpdateEconomyCarPort updateEconomyCarPort;
    private final DeleteEconomyCarPort deleteEconomyCarPort;
    private final GetEconomyCarByVinPort getEconomyCarByVinPort;
    private final GetAllRentPort getAllRentPort;
    private final UpdateRentPort updateRentPort;

    @Inject
    public EconomyCarService(AddEconomyCarPort addEconomyCarPort, GetEconomyCarByIdPort getEconomyCarByIdPort, GetAllEconomyCarPort getAllEconomyCarPort, UpdateEconomyCarPort updateEconomyCarPort, DeleteEconomyCarPort deleteEconomyCarPort, GetEconomyCarByVinPort getEconomyCarByVinPort, GetAllRentPort getAllRentPort, UpdateRentPort updateRentPort) {
        this.addEconomyCarPort = addEconomyCarPort;
        this.getEconomyCarByIdPort = getEconomyCarByIdPort;
        this.getAllEconomyCarPort = getAllEconomyCarPort;
        this.updateEconomyCarPort = updateEconomyCarPort;
        this.deleteEconomyCarPort = deleteEconomyCarPort;
        this.getEconomyCarByVinPort = getEconomyCarByVinPort;
        this.getAllRentPort = getAllRentPort;
        this.updateRentPort = updateRentPort;
    }

    @Override
    public EconomyCar add(EconomyCar car) throws RepositoryAdapterException {
        return addEconomyCarPort.add(car);
    }

    @Override
    public EconomyCar get(UUID id) throws RepositoryAdapterException {
        try {
            return getEconomyCarByIdPort.get(id);
        } catch (RepositoryEntException e) {
            throw new RepositoryAdapterException(e);
        }
    }

    @Override
    public EconomyCar get(String vin) throws RepositoryAdapterException {
        return getEconomyCarByVinPort.getEconomyCarByVin(vin);
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
    public void delete(UUID id) throws RepositoryAdapterException {
        long length = getAllRentPort.getAll().stream().filter(x -> x.getRentEndDate() == null && x.getCar().getId().equals(id)).count();
        if (length > 0) {
            throw new RepositoryAdapterException("Cannot delete already reserved vehicle");
        }
        deleteEconomyCarPort.delete(id);
        getAllRentPort.getAll().stream().filter(x -> x.getCar() != null && x.getCar().getId().equals(id)).forEach(x -> {
            x.setCar(null);
            try {
                updateRentPort.update(x);
            } catch (RepositoryAdapterException e) {
                e.printStackTrace();
            }
        });
    }
}

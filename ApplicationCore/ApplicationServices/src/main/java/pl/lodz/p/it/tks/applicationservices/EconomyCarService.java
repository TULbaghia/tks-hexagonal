package pl.lodz.p.it.tks.applicationservices;

import pl.lodz.p.it.tks.applicationports.exception.RepositoryAdapterException;
import pl.lodz.p.it.tks.applicationports.infrastructure.car.economy.*;
import pl.lodz.p.it.tks.applicationports.infrastructure.rent.GetAllRentPort;
import pl.lodz.p.it.tks.applicationports.infrastructure.rent.UpdateRentPort;
import pl.lodz.p.it.tks.applicationports.ui.EconomyCarUseCase;
import pl.lodz.p.it.tks.domainmodel.resources.EconomyCar;
import pl.lodz.p.it.tks.domainmodel.resources.Rent;
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
    @Inject
    private GetEconomyCarByVinPort getEconomyCarByVinPort;
    @Inject
    private GetAllRentPort getAllRentPort;
    @Inject
    private UpdateRentPort updateRentPort;

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
        if(length > 0) {
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

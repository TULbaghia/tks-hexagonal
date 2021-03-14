package pl.lodz.p.it.tks.applicationservices;

import pl.lodz.p.it.tks.applicationports.exception.RepositoryAdapterException;
import pl.lodz.p.it.tks.applicationports.infrastructure.car.exclusive.*;
import pl.lodz.p.it.tks.applicationports.infrastructure.rent.GetAllRentPort;
import pl.lodz.p.it.tks.applicationports.infrastructure.rent.UpdateRentPort;
import pl.lodz.p.it.tks.applicationports.ui.ExclusiveCarUseCase;
import pl.lodz.p.it.tks.domainmodel.resources.ExclusiveCar;
import pl.lodz.p.it.tks.repository.exception.RepositoryEntException;

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
    @Inject
    private GetExclusiveCarByVinPort getExclusiveCarByVinPort;
    @Inject
    private GetAllRentPort getAllRentPort;
    @Inject
    private UpdateRentPort updateRentPort;

    @Override
    public ExclusiveCar add(ExclusiveCar car) throws RepositoryAdapterException {
        return addExclusiveCarPort.add(car);
    }

    @Override
    public ExclusiveCar get(UUID id) throws RepositoryAdapterException {
        try {
            return getExclusiveCarByIdPort.get(id);
        } catch (RepositoryEntException e) {
            throw new RepositoryAdapterException(e);
        }
    }

    @Override
    public ExclusiveCar get(String vin) throws RepositoryAdapterException {
        return getExclusiveCarByVinPort.getExclusiveCarByVin(vin);
    }

    @Override
    public List<ExclusiveCar> getAll() {
        return getAllExclusiveCarPort.getAll();
    }

    @Override
    public ExclusiveCar update(ExclusiveCar car) throws RepositoryAdapterException {
        return updateExclusiveCarPort.update(car);
    }

    @Override
    public void delete(UUID id) throws RepositoryAdapterException {
        long length = getAllRentPort.getAll().stream().filter(x -> x.getRentEndDate() == null && x.getCar().getId().equals(id)).count();
        if(length > 0) {
            throw new RepositoryAdapterException("Cannot delete already reserved vehicle");
        }
        deleteExclusiveCarPort.delete(id);
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

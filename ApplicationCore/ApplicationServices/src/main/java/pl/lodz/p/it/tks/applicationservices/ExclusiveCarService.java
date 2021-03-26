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
    private final AddExclusiveCarPort addExclusiveCarPort;
    private final GetExclusiveCarByIdPort getExclusiveCarByIdPort;
    private final GetAllExclusiveCarPort getAllExclusiveCarPort;
    private final UpdateExclusiveCarPort updateExclusiveCarPort;
    private final DeleteExclusiveCarPort deleteExclusiveCarPort;
    private final GetExclusiveCarByVinPort getExclusiveCarByVinPort;
    private final GetAllRentPort getAllRentPort;
    private final UpdateRentPort updateRentPort;

    @Inject
    public ExclusiveCarService(AddExclusiveCarPort addExclusiveCarPort, GetExclusiveCarByIdPort getExclusiveCarByIdPort, GetAllExclusiveCarPort getAllExclusiveCarPort, UpdateExclusiveCarPort updateExclusiveCarPort, DeleteExclusiveCarPort deleteExclusiveCarPort, GetExclusiveCarByVinPort getExclusiveCarByVinPort, GetAllRentPort getAllRentPort, UpdateRentPort updateRentPort) {
        this.addExclusiveCarPort = addExclusiveCarPort;
        this.getExclusiveCarByIdPort = getExclusiveCarByIdPort;
        this.getAllExclusiveCarPort = getAllExclusiveCarPort;
        this.updateExclusiveCarPort = updateExclusiveCarPort;
        this.deleteExclusiveCarPort = deleteExclusiveCarPort;
        this.getExclusiveCarByVinPort = getExclusiveCarByVinPort;
        this.getAllRentPort = getAllRentPort;
        this.updateRentPort = updateRentPort;
    }

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
        if (length > 0) {
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

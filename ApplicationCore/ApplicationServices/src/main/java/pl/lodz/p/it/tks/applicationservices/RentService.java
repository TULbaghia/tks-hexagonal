package pl.lodz.p.it.tks.applicationservices;

import pl.lodz.p.it.tks.applicationports.exception.RepositoryAdapterException;
import pl.lodz.p.it.tks.applicationports.infrastructure.rent.*;
import pl.lodz.p.it.tks.applicationports.ui.RentUseCase;
import pl.lodz.p.it.tks.domainmodel.resources.Rent;

import javax.inject.Inject;
import java.util.List;
import java.util.UUID;

public class RentService implements RentUseCase {

    @Inject
    private AddRentPort addRentPort;
    @Inject
    private GetRentPort getRentPort;
    @Inject
    private GetAllRentPort getAllRentPort;
    @Inject
    private UpdateRentPort updateRentPort;
    @Inject
    private DeleteRentPort deleteRentPort;

    @Override
    public Rent add(Rent rent) throws RepositoryAdapterException {
       return addRentPort.add(rent);
    }

    @Override
    public Rent update(Rent rent) throws RepositoryAdapterException {
        return updateRentPort.update(rent);
    }

    @Override
    public List<Rent> getAll() {
        return getAllRentPort.getAll();
    }

    @Override
    public Rent get(UUID uuid) throws RepositoryAdapterException {
        return getRentPort.get(uuid);
    }

    @Override
    public void delete(UUID id) {
        deleteRentPort.delete(id);
    }
}

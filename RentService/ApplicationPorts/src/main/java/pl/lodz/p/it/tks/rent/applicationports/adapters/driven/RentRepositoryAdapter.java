package pl.lodz.p.it.tks.rent.applicationports.adapters.driven;

import pl.lodz.p.it.tks.rent.applicationports.converters.RentConverter;
import pl.lodz.p.it.tks.rent.applicationports.exception.RepositoryAdapterException;
import pl.lodz.p.it.tks.rent.applicationports.infrastructure.rent.*;
import pl.lodz.p.it.tks.rent.data.resources.CarEnt;
import pl.lodz.p.it.tks.rent.data.resources.RentEnt;
import pl.lodz.p.it.tks.rent.data.user.CustomerEnt;
import pl.lodz.p.it.tks.rent.domainmodel.resources.Rent;
import pl.lodz.p.it.tks.rent.applicationports.infrastructure.rent.*;
import pl.lodz.p.it.tks.rent.applicationports.infrastructure.rent.*;
import pl.lodz.p.it.tks.rent.repository.CarEntRepository;
import pl.lodz.p.it.tks.rent.repository.RentEntRepository;
import pl.lodz.p.it.tks.rent.repository.UserEntRepository;
import pl.lodz.p.it.tks.rent.repository.exception.RepositoryEntException;

import javax.inject.Inject;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class RentRepositoryAdapter implements AddRentPort, GetRentPort, GetAllRentPort, UpdateRentPort, DeleteRentPort {

    private final RentEntRepository rentEntRepository;
    private final UserEntRepository userEntRepository;
    private final CarEntRepository carEntRepository;

    @Inject
    public RentRepositoryAdapter(RentEntRepository rentEntRepository, UserEntRepository userEntRepository, CarEntRepository carEntRepository) {
        this.rentEntRepository = rentEntRepository;
        this.userEntRepository = userEntRepository;
        this.carEntRepository = carEntRepository;
    }

    @Override
    public Rent add(Rent rent) throws RepositoryAdapterException {
        RentEnt rentEnt = loadEnts(rent);
        try {
            return RentConverter.convertEntToDomain(rentEntRepository.add(rentEnt));
        } catch (RepositoryEntException e) {
            throw new RepositoryAdapterException(e);
        }
    }

    @Override
    public void delete(UUID id) throws RepositoryAdapterException {
        try {
            rentEntRepository.delete(id);
        } catch (RepositoryEntException e) {
            throw new RepositoryAdapterException(e);
        }
    }

    @Override
    public List<Rent> getAll() {
        return rentEntRepository.getAll().stream()
                .map(RentConverter::convertEntToDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Rent get(UUID uuid) throws RepositoryAdapterException {
        try {
            return RentConverter.convertEntToDomain(rentEntRepository.get(uuid));
        } catch (RepositoryEntException e) {
            throw new RepositoryAdapterException(e);
        }
    }

    @Override
    public Rent update(Rent rent) throws RepositoryAdapterException {
        RentEnt rentEnt = loadEnts(rent);
        try {
            return RentConverter.convertEntToDomain(rentEntRepository.update(rentEnt));
        } catch (RepositoryEntException e) {
            throw new RepositoryAdapterException(e);
        }
    }

    private RentEnt loadEnts(Rent rent) throws RepositoryAdapterException {
        CarEnt carEnt = null;
        CustomerEnt userEnt;
        try {
            if (rent.getCar() != null) {
                carEnt = carEntRepository.get(rent.getCar().getId());
            }
            userEnt = (CustomerEnt) userEntRepository.get(rent.getCustomer().getId());
        } catch (RepositoryEntException e) {
            throw new RepositoryAdapterException(e);
        }
        return RentConverter.convertDomainToEnt(rent, userEnt, carEnt);
    }
}
package pl.lodz.p.it.tks.applicationports.adapters.driven;

import pl.lodz.p.it.tks.applicationports.converters.RentConverter;
import pl.lodz.p.it.tks.applicationports.exception.RepositoryAdapterException;
import pl.lodz.p.it.tks.applicationports.infrastructure.rent.*;
import pl.lodz.p.it.tks.data.resources.RentEnt;
import pl.lodz.p.it.tks.domainmodel.resources.Rent;
import pl.lodz.p.it.tks.repository.RentEntRepository;
import pl.lodz.p.it.tks.repository.exception.RepositoryEntException;

import javax.inject.Inject;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class RentRepositoryAdapter implements AddRentPort, GetRentPort, GetAllRentPort, UpdateRentPort, DeleteRentPort, EndRentPort {

    @Inject
    private RentEntRepository rentEntRepository;

    @Override
    public Rent add(Rent rent) throws RepositoryAdapterException {
        RentEnt rentEnt = RentConverter.convertDomainToEnt(rent);
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
        RentEnt rentEnt = RentConverter.convertDomainToEnt(rent);
        try {
            return RentConverter.convertEntToDomain(rentEntRepository.update(rentEnt));
        } catch (RepositoryEntException e) {
            throw new RepositoryAdapterException(e);
        }
    }

    @Override
    public Rent endRent(UUID id) throws RepositoryAdapterException {
        try {
            return RentConverter.convertEntToDomain(rentEntRepository.endRent(id));
        } catch (RepositoryEntException e) {
            throw new RepositoryAdapterException(e);
        }
    }
}
package pl.lodz.p.it.tks.applicationports.adapters.driven;

import lombok.NonNull;
import pl.lodz.p.it.tks.domainmodel.resource.FiveStarApartment;
import pl.lodz.p.it.tks.applicationports.converters.ApartmentConverter;
import pl.lodz.p.it.tks.applicationports.infrastructure.*;
import pl.lodz.p.it.tks.data.resource.FiveStarApartmentEnt;
import pl.lodz.p.it.tks.repository.ApartmentEntRepositoryInterface;
import pl.lodz.p.it.tks.repository.RepositoryException;

import javax.inject.Inject;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

class FiveStarRepositoryAdapter implements ApartmentGetByDoorPort<FiveStarApartment>,
        AddItemPort<FiveStarApartment>, GetAllItemPort<FiveStarApartment>,
        GetItemByIdPort<FiveStarApartment>, UpdateItemPort<FiveStarApartment>, DeleteItemPort<FiveStarApartment> {

    @Inject
    private ApartmentEntRepositoryInterface apartmentEntRepositoryInterface;

    @Override
    public void add(@NonNull FiveStarApartment item) throws RepositoryException {
        FiveStarApartmentEnt fse = ApartmentConverter.convertDomainToEnt(item);
        apartmentEntRepositoryInterface.add(fse);
    }

    @Override
    public void delete(@NonNull UUID id) throws RepositoryException {
        apartmentEntRepositoryInterface.delete(id);
    }

    @Override
    public List<FiveStarApartment> getAll() {
        return apartmentEntRepositoryInterface.getAll()
                .stream()
                .filter(x -> x instanceof FiveStarApartmentEnt)
                .map(x -> ApartmentConverter.convertEntToDomain((FiveStarApartmentEnt) x))
                .collect(Collectors.toList());
    }

    @Override
    public FiveStarApartment get(UUID id) {
        return ApartmentConverter.convertEntToDomain((FiveStarApartmentEnt) apartmentEntRepositoryInterface.get(id));
    }

    @Override
    public void update(@NonNull FiveStarApartment item) throws RepositoryException {
        apartmentEntRepositoryInterface.update(ApartmentConverter.convertDomainToEnt(item));
    }

    @Override
    public FiveStarApartment get(int doorNumber) {
        return ApartmentConverter.convertEntToDomain((FiveStarApartmentEnt) apartmentEntRepositoryInterface.get(doorNumber));
    }
}

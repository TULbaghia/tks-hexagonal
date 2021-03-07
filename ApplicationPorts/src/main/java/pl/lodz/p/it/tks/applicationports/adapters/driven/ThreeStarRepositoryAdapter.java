package pl.lodz.p.it.tks.applicationports.adapters.driven;

import lombok.NonNull;
import pl.lodz.p.it.tks.applicationcore.domainmodel.resource.ThreeStarApartment;
import pl.lodz.p.it.tks.applicationports.converters.ApartmentConverter;
import pl.lodz.p.it.tks.applicationports.out.*;
import pl.lodz.p.it.tks.data.model.resource.ThreeStarApartmentEnt;
import pl.lodz.p.it.tks.data.repository.ApartmentEntRepositoryInterface;
import pl.lodz.p.it.tks.data.repository.RepositoryException;

import javax.inject.Inject;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

class ThreeStarRepositoryAdapter implements ApartmentGetByDoor<ThreeStarApartment>, AddItem<ThreeStarApartment>,
        GetAllItem<ThreeStarApartment>, GetItemById<ThreeStarApartment>, UpdateItem<ThreeStarApartment>,
        DeleteItem<ThreeStarApartment> {

    @Inject
    private ApartmentEntRepositoryInterface apartmentEntRepositoryInterface;

    @Override
    public void add(@NonNull ThreeStarApartment item) throws RepositoryException {
        ThreeStarApartmentEnt fse = ApartmentConverter.convertDomainToEnt(item);
        apartmentEntRepositoryInterface.add(fse);
    }

    @Override
    public void delete(@NonNull UUID id) throws RepositoryException {
        apartmentEntRepositoryInterface.delete(id);
    }

    @Override
    public List<ThreeStarApartment> getAll() {
        return apartmentEntRepositoryInterface.getAll()
                .stream()
                .filter(x -> x instanceof ThreeStarApartmentEnt)
                .map(x -> ApartmentConverter.convertEntToDomain((ThreeStarApartmentEnt) x))
                .collect(Collectors.toList());
    }

    @Override
    public ThreeStarApartment get(UUID id) {
        return ApartmentConverter.convertEntToDomain((ThreeStarApartmentEnt) apartmentEntRepositoryInterface.get(id));
    }

    @Override
    public void update(@NonNull ThreeStarApartment item) throws RepositoryException {
        apartmentEntRepositoryInterface.update(ApartmentConverter.convertDomainToEnt(item));
    }

    @Override
    public ThreeStarApartment get(int doorNumber) {
        return ApartmentConverter.convertEntToDomain((ThreeStarApartmentEnt) apartmentEntRepositoryInterface.get(doorNumber));
    }
}

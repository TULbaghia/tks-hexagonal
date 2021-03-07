package pl.lodz.p.it.tks.applicationcore.domainservice;

import lombok.NonNull;
import pl.lodz.p.it.tks.applicationcore.domainmodel.resource.Apartment;
import pl.lodz.p.it.tks.applicationports.in.ApartmentPort;
import pl.lodz.p.it.tks.applicationports.out.*;
import pl.lodz.p.it.tks.data.repository.RepositoryException;

import javax.inject.Inject;
import java.util.List;
import java.util.UUID;

public class ApartmentManager implements ApartmentPort {

    // Dwa różne adaptery, nie wie, który ma wykorzystać w danym przypadku.
    @Inject
    private AddItem<Apartment> addItem;
    @Inject
    private GetItemById<Apartment> getItemById;
    @Inject
    private GetAllItem<Apartment> getAllItem;
    @Inject
    private UpdateItem<Apartment> updateItem;
    @Inject
    private DeleteItem<Apartment> deleteItem;
    @Inject
    private ApartmentGetByDoor<Apartment> getByDoorNumber;


    @Override
    public void add(@NonNull Apartment apartment) throws RepositoryException {
        addItem.add(apartment);
    }

    @Override
    public Apartment get(UUID id) {
        return getItemById.get(id);
    }

    @Override
    public Apartment get(int doorNumber) {
        return getByDoorNumber.get(doorNumber);
    }

    @Override
    public List<Apartment> getAll() {
        return getAllItem.getAll();
    }

    @Override
    public void update(@NonNull Apartment apartment) throws RepositoryException {
        updateItem.update(apartment);
    }

    @Override
    public void delete(@NonNull UUID id) throws RepositoryException {
        deleteItem.delete(id);
    }
}

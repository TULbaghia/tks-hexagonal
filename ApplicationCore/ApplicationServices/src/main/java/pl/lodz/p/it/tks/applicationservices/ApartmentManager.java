package pl.lodz.p.it.tks.applicationservices;

import lombok.NonNull;
import pl.lodz.p.it.tks.applicationports.infrastructure.*;
import pl.lodz.p.it.tks.applicationports.ui.ApartmentUseCase;
import pl.lodz.p.it.tks.repository.RepositoryException;
import pl.lodz.p.it.tks.domainmodel.resource.Apartment;

import javax.inject.Inject;
import java.util.List;
import java.util.UUID;

// TODO: Dwa różne adaptery (5star, 3star), kontener nie wie, który z nich ma wykorzystać w danym przypadku.
public class ApartmentManager implements ApartmentUseCase {

    @Inject
    private AddItemPort<Apartment> addItem;
    @Inject
    private GetItemByIdPort<Apartment> getItemById;
    @Inject
    private GetAllItemPort<Apartment> getAllItem;
    @Inject
    private UpdateItemPort<Apartment> updateItem;
    @Inject
    private DeleteItemPort<Apartment> deleteItem;
    @Inject
    private ApartmentGetByDoorPort<Apartment> getByDoorNumber;


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

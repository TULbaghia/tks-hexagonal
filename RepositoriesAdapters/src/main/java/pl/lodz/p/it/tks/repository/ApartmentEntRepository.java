package pl.lodz.p.it.tks.repository;

import lombok.NonNull;
import pl.lodz.p.it.tks.data.resource.ApartmentEnt;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ApartmentEntRepository extends Repository<ApartmentEnt> implements ApartmentEntRepositoryInterface {

    public ApartmentEnt get(int doorNumber) {
        return filter(x -> x.getDoorNumber() == doorNumber).stream().findFirst().orElse(null);
    }

    @Override
    public synchronized void add(@NonNull ApartmentEnt item) throws RepositoryException {
        if (get(item.getDoorNumber()) != null) {
            throw new RepositoryException("doorNumberAlreadyExist");
        }
        super.add(item);
    }

    @Override
    public synchronized void update(@NonNull ApartmentEnt item) throws RepositoryException {
        if(filter(x -> x.getDoorNumber() == item.getDoorNumber() && !x.getId().equals(item.getId())).size() > 0) {
            throw new RepositoryException("doorNumberAlreadyExist");
        }
        super.update(item);
    }
}

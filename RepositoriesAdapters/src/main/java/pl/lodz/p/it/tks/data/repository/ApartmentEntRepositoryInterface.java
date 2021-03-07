package pl.lodz.p.it.tks.data.repository;

import pl.lodz.p.it.tks.data.model.resource.ApartmentEnt;
import pl.lodz.p.it.tks.data.repository.RepositoryInterface;

public interface ApartmentEntRepositoryInterface extends RepositoryInterface<ApartmentEnt> {
    ApartmentEnt get(int doorNumber);
}

package pl.lodz.p.it.tks.repository;

import pl.lodz.p.it.tks.data.resource.ApartmentEnt;

public interface ApartmentEntRepositoryInterface extends RepositoryInterface<ApartmentEnt> {
    ApartmentEnt get(int doorNumber);
}

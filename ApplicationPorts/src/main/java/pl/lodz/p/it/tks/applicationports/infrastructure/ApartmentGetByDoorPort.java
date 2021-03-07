package pl.lodz.p.it.tks.applicationports.infrastructure;

import pl.lodz.p.it.tks.domainmodel.resource.Apartment;

public interface ApartmentGetByDoorPort<T extends Apartment> {
    T get(int doorNumber);
}

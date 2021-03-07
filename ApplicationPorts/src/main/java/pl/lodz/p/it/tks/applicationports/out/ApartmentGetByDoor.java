package pl.lodz.p.it.tks.applicationports.out;

import pl.lodz.p.it.tks.applicationcore.domainmodel.resource.Apartment;

public interface ApartmentGetByDoor<T extends Apartment> {
    T get(int doorNumber);
}

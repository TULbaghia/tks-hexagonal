package pl.lodz.p.it.tks.rent.applicationports.infrastructure.rent;

import pl.lodz.p.it.tks.rent.domainmodel.resources.Rent;

import java.util.List;

public interface GetAllRentPort {
    List<Rent> getAll();
}

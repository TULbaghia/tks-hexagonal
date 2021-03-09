package pl.lodz.p.it.tks.applicationports.infrastructure.rent;

import pl.lodz.p.it.tks.domainmodel.resources.Rent;

import java.util.List;

public interface GetAllRentPort {
    List<Rent> getAll();
}

package pl.lodz.p.it.tks.applicationports.infrastructure;

import pl.lodz.p.it.tks.domainmodel.trait.IdTrait;

import java.util.List;

public interface GetAllItemPort<T extends IdTrait> {
    List<T> getAll();
}

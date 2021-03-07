package pl.lodz.p.it.tks.applicationports.infrastructure;

import pl.lodz.p.it.tks.domainmodel.trait.IdTrait;

import java.util.UUID;

public interface GetItemByIdPort<T extends IdTrait> {
    T get(UUID id);
}

package pl.lodz.p.it.tks.applicationports.out;

import pl.lodz.p.it.tks.applicationcore.domainmodel.trait.IdTrait;

import java.util.UUID;

public interface GetItemById<T extends IdTrait> {
    T get(UUID id);
}

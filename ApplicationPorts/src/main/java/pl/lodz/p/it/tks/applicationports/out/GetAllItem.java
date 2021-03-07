package pl.lodz.p.it.tks.applicationports.out;

import pl.lodz.p.it.tks.applicationcore.domainmodel.trait.IdTrait;

import java.util.List;

public interface GetAllItem<T extends IdTrait> {
    List<T> getAll();
}

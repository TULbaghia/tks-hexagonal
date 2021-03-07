package pl.lodz.p.it.tks.applicationports.infrastructure;

import lombok.NonNull;
import pl.lodz.p.it.tks.domainmodel.trait.IdTrait;
import pl.lodz.p.it.tks.repository.RepositoryException;

public interface UpdateItemPort<T extends IdTrait> {
    void update(@NonNull T item) throws RepositoryException;
}

package pl.lodz.p.it.tks.applicationports.out;

import lombok.NonNull;
import pl.lodz.p.it.tks.applicationcore.domainmodel.trait.IdTrait;
import pl.lodz.p.it.tks.data.repository.RepositoryException;

public interface UpdateItem<T extends IdTrait> {
    void update(@NonNull T item) throws RepositoryException;
}

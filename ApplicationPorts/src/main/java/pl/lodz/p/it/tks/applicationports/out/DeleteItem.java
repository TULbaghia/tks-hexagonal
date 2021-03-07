package pl.lodz.p.it.tks.applicationports.out;

import lombok.NonNull;
import pl.lodz.p.it.tks.applicationcore.domainmodel.trait.IdTrait;
import pl.lodz.p.it.tks.data.repository.RepositoryException;

import java.util.UUID;

public interface DeleteItem<T extends IdTrait> {
    void delete(@NonNull UUID id) throws RepositoryException;
}

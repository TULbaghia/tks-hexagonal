package pl.lodz.p.it.tks.applicationports.infrastructure;

import lombok.NonNull;
import pl.lodz.p.it.tks.domainmodel.trait.IdTrait;
import pl.lodz.p.it.tks.repository.RepositoryException;

import java.util.UUID;

public interface DeleteItemPort<T extends IdTrait> {
    void delete(@NonNull UUID id) throws RepositoryException;
}

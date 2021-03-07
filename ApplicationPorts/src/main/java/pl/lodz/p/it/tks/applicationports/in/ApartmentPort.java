package pl.lodz.p.it.tks.applicationports.in;

import lombok.NonNull;
import pl.lodz.p.it.tks.applicationcore.domainmodel.resource.Apartment;
import pl.lodz.p.it.tks.data.repository.RepositoryException;

import java.util.List;
import java.util.UUID;

public interface ApartmentPort {
    void add(@NonNull Apartment apartment) throws RepositoryException;

    Apartment get(UUID id);

    Apartment get(int doorNumber);

    List<Apartment> getAll();

    void update(@NonNull Apartment apartment) throws RepositoryException;

    void delete(@NonNull UUID id) throws RepositoryException;
}

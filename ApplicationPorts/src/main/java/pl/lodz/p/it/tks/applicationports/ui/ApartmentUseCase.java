package pl.lodz.p.it.tks.applicationports.ui;

import lombok.NonNull;
import pl.lodz.p.it.tks.repository.RepositoryException;
import pl.lodz.p.it.tks.domainmodel.resource.Apartment;

import java.util.List;
import java.util.UUID;

public interface ApartmentUseCase {
    void add(@NonNull Apartment apartment) throws RepositoryException;

    Apartment get(UUID id);

    Apartment get(int doorNumber);

    List<Apartment> getAll();

    void update(@NonNull Apartment apartment) throws RepositoryException;

    void delete(@NonNull UUID id) throws RepositoryException;
}

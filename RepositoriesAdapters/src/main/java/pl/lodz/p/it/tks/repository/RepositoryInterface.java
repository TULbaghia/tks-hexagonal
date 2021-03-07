package pl.lodz.p.it.tks.repository;

import lombok.NonNull;
import pl.lodz.p.it.tks.data.trait.IdTraitEnt;

import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

public interface RepositoryInterface<T extends IdTraitEnt> {
    void add(@NonNull T item) throws RepositoryException;

    T get(UUID id);

    List<T> getAll();

    void update(@NonNull T item) throws RepositoryException;

    void delete(@NonNull UUID id) throws RepositoryException;

    List<T> filter(Predicate<T> predicate);
}

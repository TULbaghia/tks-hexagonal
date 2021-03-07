package pl.lodz.p.it.tks.data.repository;

import lombok.NonNull;
import org.apache.commons.beanutils.BeanUtils;
import pl.lodz.p.it.tks.data.model.trait.IdTraitEnt;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public abstract class Repository<T extends IdTraitEnt> implements RepositoryInterface<T> {
    private final List<T> items = new ArrayList<>();

    public T get(UUID id) {
        return filter(x -> x.getId().equals(id)).stream().findFirst().orElse(null);
    }

    public List<T> getAll() {
        return new ArrayList<>(items);
    }

    public synchronized void add(@NonNull T item) throws RepositoryException {
        if (item.getId() == null) {
            item.setId(UUID.randomUUID());
        }
        if (get(item.getId()) == null) {
            items.add(item);
        } else {
            throw new RepositoryException("objectAlreadyExist");
        }
    }

    public synchronized void update(@NonNull T item) throws RepositoryException {
        if(item.getId() == null) {
            throw new RepositoryException("objectIdIsNull");
        }
        T old = get(item.getId());
        if (old != null) {
            try {
                BeanUtils.copyProperties(old, item);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new RepositoryException(e);
            }
        } else {
            throw new RepositoryException("objectDoesNotExist");
        }
    }

    public synchronized void delete(@NonNull UUID id) throws RepositoryException {
        T old = get(id);
        if (old != null) {
            items.remove(old);
        } else {
            throw new RepositoryException("objectDoesNotExist");
        }
    }

    public List<T> filter(Predicate<T> predicate) {
        return items.stream().filter(predicate).collect(Collectors.toList());
    }
}
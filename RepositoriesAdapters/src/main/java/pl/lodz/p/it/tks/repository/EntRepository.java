package pl.lodz.p.it.tks.repository;

import org.apache.commons.beanutils.BeanUtils;
import pl.lodz.p.it.tks.data.trait.ModelIdTraitEnt;
import pl.lodz.p.it.tks.repository.exception.RepositoryEntException;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class EntRepository<T extends ModelIdTraitEnt> {
    private final List<T> items = new ArrayList<>();

    public synchronized T add(T item) throws RepositoryEntException {
        if (item.getId() != null) {
//            throw new RepositoryEntException("ID must be null.");
        } else {
            item.setId(UUID.randomUUID());
        }
        items.add(item);
        return get(item.getId());
    }

    public T get(UUID id) throws RepositoryEntException {
        return items.stream().filter(x -> x.getId().equals(id)).findFirst().orElseThrow(() -> new RepositoryEntException("Item does not exist."));
    }

    public List<T> getAll() {
        return new ArrayList<>(items);
    }

    public synchronized T update(T item) throws RepositoryEntException {
        if(item.getId() == null) {
            throw new RepositoryEntException("Item id is null.");
        }
        T old = get(item.getId());
        try {
            BeanUtils.copyProperties(old, item);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RepositoryEntException(e);
        }
        return get(item.getId());
    }

    public synchronized void delete(UUID id) throws RepositoryEntException {
        T old = get(id);
        items.remove(old);
    }
}

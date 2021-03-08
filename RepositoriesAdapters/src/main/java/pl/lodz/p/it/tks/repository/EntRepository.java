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

    public synchronized void add(T item) throws RepositoryEntException {
        if (item.getId() == null) {
            item.setId(UUID.randomUUID());
        }
        if (get(item.getId()) == null) {
            items.add(item);
        } else {
            throw new RepositoryEntException("Item is already added.");
        }
    }

    public T get(UUID id) {
        return items.stream().filter(x -> x.getId().equals(id)).findFirst().orElse(null);
    }

    public List<T> getAll() {
        return new ArrayList<>(items);
    }

    public synchronized void update(T item) throws RepositoryEntException, InvocationTargetException, IllegalAccessException {
        if(item.getId() == null) {
            throw new RepositoryEntException("Item id is null.");
        }
        T old = get(item.getId());
        if (old != null) {
            BeanUtils.copyProperties(old, item);
        } else {
            throw new RepositoryEntException("Item does not exist.");
        }
    }

    public synchronized void delete(UUID id) {
        T old = get(id);
        if (old != null) {
            items.remove(old);
        }
    }
}

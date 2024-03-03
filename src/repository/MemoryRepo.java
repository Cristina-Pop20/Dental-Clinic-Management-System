package repository;

import domain.Identifiable;

import java.util.HashMap;
import java.util.Map;

public class MemoryRepo<T extends Identifiable<ID>, ID> implements InterRepo<T, ID> {
    public Map<ID, T> elements = new HashMap();

    public MemoryRepo() {
    }

    public void addItem(T item) {
        if (this.elements.containsKey(item.getId())) {
            throw new IllegalArgumentException("This item already exists");
        } else {
            this.elements.put(item.getId(), item);
        }

    }

    public void removeItem(ID id) {
        if (!this.elements.containsKey(id)) {
            throw new IllegalArgumentException("Entity with ID " + String.valueOf(id) + " not found");
        } else {
            this.elements.remove(id);
        }
    }

    public void update(T entity, ID id) {
        if (this.elements.containsKey(id)) {
            this.elements.put(id, entity);
        } else {
            throw new IllegalArgumentException("Entity with ID " + String.valueOf(id) + " not found");
        }
    }

    public T findItem(ID id) {

        return this.elements.get(id);
    }

    public Iterable<T> getAllItems() {
        return this.elements.values();
    }

}

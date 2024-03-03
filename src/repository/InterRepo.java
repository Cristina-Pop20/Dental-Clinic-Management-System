package repository;

import domain.Identifiable;

public interface InterRepo<T extends Identifiable, ID> {
    void addItem(T var1) throws Exception;

    void removeItem(ID var) throws Exception;

    void update(T var1, ID var2) throws Exception;

    T findItem(ID var1);

    Iterable<T> getAllItems();
}

package nl.pancompany.unicorn.application.unicorn.dao;

public interface Dao<T, ID> {
    T find(ID id);

    T add(T t);

    T update(T t);

    long count();
}

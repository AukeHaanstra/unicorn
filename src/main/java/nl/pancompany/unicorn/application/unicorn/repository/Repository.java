package nl.pancompany.unicorn.application.unicorn.repository;

public interface Repository<T, ID> {
    T find(ID id);

    T add(T t);

    T update(T t);

    long count();
}

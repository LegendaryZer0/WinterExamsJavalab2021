package ru.itis.repository;

import java.util.Optional;

public interface CrudRepository<ID,T> {
    void create(T entity);
    void update(T entity);
    void delete(T entity);
    Optional<T> findById(long id);
}

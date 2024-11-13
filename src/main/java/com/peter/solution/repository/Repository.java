package com.peter.solution.repository;

import java.util.List;

/**
 * Generic interface for basic CRUD operations on entities of type T with identifier type ID.
 *
 * @param <T>  the type of the entity
 * @param <ID> the type of the entity's identifier
 */
public interface Repository<T, ID> {

    /**
     * Retrieves all entities of type T.
     *
     * @return a list of all entities of type T
     */
    List<T> findAll();

    /**
     * Saves the given entity.
     *
     * @param entity the entity to save
     * @return the saved entity
     */
    T save(T entity);

    /**
     * Deletes an entity by its identifier.
     *
     * @param id the identifier of the entity to delete
     */
    void deleteById(ID id);
}
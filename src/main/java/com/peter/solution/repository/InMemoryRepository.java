package com.peter.solution.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Abstract base class for in-memory repository implementation.
 * Provides basic CRUD operations for entities of type T with a Long identifier.
 *
 * @param <T> the type of entity managed by this repository
 */
public abstract class InMemoryRepository<T extends BaseEntity<Long>> implements Repository<T, Long> {

    private final Map<Long, T> storage = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    /**
     * Retrieves all entities of type T stored in the repository.
     *
     * @return a list of all entities in the repository
     */
    @Override
    public List<T> findAll() {
        return new ArrayList<>(storage.values());
    }

    /**
     * Saves the given entity in the repository.
     *
     * @param entity the entity to save
     * @return the saved entity
     */
    @Override
    public T save(T entity) {
        Long id = generateId();
        entity.setId(id);
        storage.put(id, entity);
        return entity;
    }

    /**
     * Deletes the entity with the given identifier from the repository.
     *
     * @param id the identifier of the entity to delete
     */
    @Override
    public void deleteById(Long id) {
        storage.remove(id);
    }

    /**
     * Generates a unique identifier for a new entity.
     *
     * @return a new unique identifier
     */
    protected Long generateId() {
        return idGenerator.getAndIncrement();
    }

}
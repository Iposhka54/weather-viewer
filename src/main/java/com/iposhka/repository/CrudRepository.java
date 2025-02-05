package com.iposhka.repository;

import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@Repository
public interface CrudRepository<ID extends Serializable, E> {

    E save(E entity);

    Optional<E> findById(ID id);

    void delete(ID id);

    E update(E entity);

    List<E> findAll();
}

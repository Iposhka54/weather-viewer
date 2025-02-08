package com.iposhka.repository;

import com.iposhka.exception.DatabaseException;
import com.iposhka.exception.UserAlreadyExistException;
import jakarta.persistence.criteria.CriteriaQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@Repository
public abstract class BaseRepository<ID extends Serializable, E >
        implements CrudRepository<ID, E> {

    private final Class<E> entityClass;

    protected final SessionFactory sessionFactory;

    protected BaseRepository(Class<E> entityClass, SessionFactory sessionFactory) {
        this.entityClass = entityClass;
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void save(E entity){
        try{
            Session session = sessionFactory.getCurrentSession();
            session.persist(entity);

        }catch(ConstraintViolationException e){
            throw new UserAlreadyExistException("Entity already exist");
        }
        catch(Exception e){
            throw new DatabaseException("Problems with saving in database");
        }
    }

    @Override
    public Optional<E> findById(ID id) {
        try{
            Session session = sessionFactory.getCurrentSession();

            E e = session.find(entityClass, id);

            return Optional.ofNullable(e);
        }catch(Exception e){
            throw new DatabaseException("Problem with finding by id in database");
        }
    }

    @Override
    public void delete(ID id) {
        try{
            Session session = sessionFactory.getCurrentSession();

            E e = session.find(entityClass, id);

            session.remove(e);

        }catch(Exception e){
            throw new DatabaseException("Problem with deleting from database");
        }
    }

    @Override
    public E update(E entity) {
        try{
            Session session = sessionFactory.getCurrentSession();
            session.merge(entity);

            return entity;
        }catch(Exception e){
            throw new DatabaseException("Problem with updating in database!");
        }
    }

    @Override
    public List<E> findAll() {
        try{
            Session session = sessionFactory.getCurrentSession();
            CriteriaQuery<E> criteria = session.getCriteriaBuilder().createQuery(entityClass);
            criteria.from(entityClass);

            return session.createQuery(criteria).getResultList();
        }catch(Exception e){
            throw new DatabaseException("Problem with selecting all from database!");
        }
    }
}
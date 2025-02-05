package com.iposhka.repository;

import com.iposhka.exception.DatabaseException;
import com.iposhka.model.Session;
import jakarta.persistence.NoResultException;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class SessionRepository extends BaseRepository<Integer, Session>{
    public SessionRepository(SessionFactory sessionFactory) {
        super(Session.class, sessionFactory);
    }

    public Optional<Session> findByUserId(Integer id){
        try{
            org.hibernate.Session session = sessionFactory.getCurrentSession();

            Session entity = session.createQuery("SELECT s FROM Session AS s WHERE s.user.id = :id", Session.class)
                    .setParameter("id", id)
                    .getSingleResult();

            return Optional.of(entity);
        }catch (NoResultException e){
            return Optional.empty();
        }
        catch (Exception e){
            throw new DatabaseException("Any problems with database in find session by user id");
        }
    }

    public Optional<Session> findByUUID(String uuid){
        try{
            org.hibernate.Session session = sessionFactory.getCurrentSession();

            Session entity = session.createQuery("SELECT s FROM Session AS s WHERE s.id = :id", Session.class)
                    .setParameter("id", uuid)
                    .getSingleResult();
            return Optional.of(entity);
        }catch (NoResultException e){
            return Optional.empty();
        }
    }
}

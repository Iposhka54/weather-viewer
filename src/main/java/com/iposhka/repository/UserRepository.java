package com.iposhka.repository;

import com.iposhka.exception.DatabaseException;
import com.iposhka.model.User;
import jakarta.transaction.Transactional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserRepository extends BaseRepository<Integer, User> {

    public UserRepository(SessionFactory sessionFactory) {
        super(User.class, sessionFactory);
    }

    public Optional<User> findByLogin(String login) {
        try{
            Session session = sessionFactory.getCurrentSession();
            User entity = session.createQuery("SELECT u FROM User AS u WHERE u.login = :login", User.class)
                    .setParameter("login", login)
                    .getSingleResult();

            return Optional.ofNullable(entity);
        }catch (Exception e){
            throw new DatabaseException("Any problem with database");
        }
    }
}

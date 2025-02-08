package com.iposhka.repository;

import com.iposhka.exception.DatabaseException;
import com.iposhka.model.Location;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

@Repository
public class LocationRepository extends BaseRepository<Integer, Location>{
    protected LocationRepository(SessionFactory sessionFactory) {
        super(Location.class, sessionFactory);
    }

    public void deleteByIdAndUserId(int locationId, int userId){
        try{
            Session session = sessionFactory.getCurrentSession();
            session.createQuery("DELETE FROM Location as l WHERE l.id = :locationId AND l.user.id = :userId")
                    .setParameter("locationId", locationId)
                    .setParameter("userId", userId)
                    .executeUpdate();
        }catch(Exception e){
            throw new DatabaseException("Any problems with database with delete location");
        }
    }
}

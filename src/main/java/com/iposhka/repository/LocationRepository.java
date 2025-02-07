package com.iposhka.repository;

import com.iposhka.model.Location;
import org.hibernate.SessionFactory;

public class LocationRepository extends BaseRepository<Integer, Location>{
    protected LocationRepository(SessionFactory sessionFactory) {
        super(Location.class, sessionFactory);
    }
}

package com.example.demo.DAO;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import org.hibernate.SessionFactory;

public class DefaultDAO {
    private static SessionFactory sessionFactory;
    private static EntityManager entityManager;

    public SessionFactory getSessionFactory() {
        if(sessionFactory == null){
            sessionFactory =
                    (SessionFactory) Persistence
                            .createEntityManagerFactory("my-persistence-unit");
        }
        return sessionFactory;
    }

    public EntityManager getEntityManager() {
        if(entityManager == null){
            SessionFactory sf = getSessionFactory();
            entityManager = sf.createEntityManager();
        }
        return entityManager;
    }

    public void beginTransaction(){
        getEntityManager().getTransaction().begin();
    }

    public void commitTransaction(){
        getEntityManager().getTransaction().commit();
    }
}

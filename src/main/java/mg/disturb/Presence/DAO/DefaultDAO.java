package mg.disturb.Presence.DAO;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.hibernate.SessionFactory;

public class DefaultDAO {
    private static SessionFactory sessionFactory;
    private static EntityManager entityManager;
    private static CriteriaBuilder criteriaBuilder;

    protected CriteriaBuilder getCriteriaBuilder() {
        if(criteriaBuilder == null){
            criteriaBuilder = getEntityManager().getCriteriaBuilder();
        }
        return criteriaBuilder;
    }

    protected SessionFactory getSessionFactory() {
        if(sessionFactory == null){
            sessionFactory =
                    (SessionFactory) Persistence
                            .createEntityManagerFactory("my-persistence-unit");
        }
        return sessionFactory;
    }

    protected EntityManager getEntityManager() {
        if(entityManager == null){
            SessionFactory sf = getSessionFactory();
            entityManager = sf.createEntityManager();
        }
        return entityManager;
    }

    protected void beginTransaction(){
        getEntityManager().getTransaction().begin();
    }

    protected void commitTransaction(){
        getEntityManager().getTransaction().commit();
    }
}

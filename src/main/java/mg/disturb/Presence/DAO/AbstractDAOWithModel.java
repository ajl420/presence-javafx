package mg.disturb.Presence.DAO;

import jakarta.persistence.EntityManager;


import java.util.List;

public abstract class AbstractDAOWithModel<T,Id> extends DefaultDAO {
    protected Class<T> modelClass;
    public void persiste(T obj){
        EntityManager entMan = getEntityManager();
        beginTransaction();
        entMan.persist(obj);
        commitTransaction();
    }

    public T findById(Id id){
        EntityManager entMan = getEntityManager();
        T result = entMan.find(modelClass,id);
        return result;
    }

    public List<T> findAll(){
        EntityManager em = getEntityManager();
        return (List<T>) em.createQuery("SELECT r FROM "+this.modelClass.getName()+" r")
                .getResultList();
    }

    public void update(T obj){
        EntityManager entMan = getEntityManager();
        beginTransaction();
        entMan.merge(obj);
        commitTransaction();
    }

    public void delete(T obj){
        EntityManager entMan = getEntityManager();
        beginTransaction();
        entMan.remove(obj);
        commitTransaction();
    }
    public void detach(T obj){
        getEntityManager().detach(obj);
    }
}

package com.example.demo.DAO;

import com.example.demo.Model.Event;
import com.example.demo.Model.EventRepeated;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Root;

import java.sql.Time;
import java.util.List;

public class EventDAO extends AbstractDAOWithModel<Event,String> {
    public EventDAO(){
        this.modelClass = Event.class;
    }

    public List<Event> findTakenTimeIntervall(Time startTime, Time endTime){
        CriteriaBuilder cb = getCriteriaBuilder();
        CriteriaQuery<Event> q = cb.createQuery(Event.class);
        Root<Event> root = q.from(Event.class);
        q.select(root);
        q.where(
              cb.or(
                      cb.and(
                              cb.greaterThanOrEqualTo(root.get("endTime"), startTime),
                              cb.lessThanOrEqualTo(root.get("beginTime"), startTime)
                      ),
                      cb.and(
                              cb.greaterThanOrEqualTo(root.get("endTime"), endTime),
                              cb.lessThanOrEqualTo(root.get("beginTime"), endTime)
                      )
              )
        );
        return getEntityManager().createQuery(q).getResultList();
    }

    public void persiste(EventRepeated event){
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        em.persist(event);
        em.getTransaction().commit();
    }
}

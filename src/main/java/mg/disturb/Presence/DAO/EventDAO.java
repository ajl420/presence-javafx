package mg.disturb.Presence.DAO;

import jakarta.persistence.NoResultException;
import jakarta.persistence.criteria.*;
import mg.disturb.Presence.Model.Event;
import mg.disturb.Presence.Model.EventRepeated;
import mg.disturb.Presence.Model.EventSingleDay;
import mg.disturb.Presence.Model.Days;
import mg.disturb.Presence.Utils.DateUtils;

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

    public List<EventRepeated> getAllEventRepeated(){
        CriteriaBuilder cb = getCriteriaBuilder();
        CriteriaQuery<EventRepeated> q = cb.createQuery(EventRepeated.class);
        Root<EventRepeated> root = q.from(EventRepeated.class);
        q.select(root);
        return getEntityManager().createQuery(q).getResultList();
    }

    public List<EventSingleDay> getEventsSingleOfTheWeek(){
        CriteriaBuilder cb = getCriteriaBuilder();
        CriteriaQuery<EventSingleDay> q = cb.createQuery(EventSingleDay.class);
        Root<EventSingleDay> root = q.from(EventSingleDay.class);
        q.select(root);
        q.where(
                cb.between(
                        root.get("eventDate"),
                        DateUtils.getFirstWeekDay(),
                        DateUtils.getLastWeekDay()
                )
        );
        return getEntityManager().createQuery(q).getResultList();
    }

    public List<Event> getTwoNearestEvent(){
        CriteriaBuilder cb = getCriteriaBuilder();
        CriteriaQuery<Event> q = cb.createQuery(Event.class);
        Root<Event> root = q.from(Event.class);
        q.select(root);
        q.where(
                cb.and(
                        cb.greaterThanOrEqualTo(
                                root.get("endTime"),
                                DateUtils.getCurrentTime()
                        ),

                        cb.or(
                                cb.equal(
                                        root.get("eventDate"),
                                        DateUtils.getCurrentDate()
                                ),
                                cb.equal(
                                        root.get("repeatedDayId"),
                                        DateUtils.getDayOfWeek(DateUtils.getCurrentDate())
                                )
                        )
                )
        );

        q.orderBy(cb.asc(root.get("endTime")));
        try {
            return getEntityManager().createQuery(q).setMaxResults(2).getResultList();
        } catch (NoResultException e){
            return null;
        }
    }

}

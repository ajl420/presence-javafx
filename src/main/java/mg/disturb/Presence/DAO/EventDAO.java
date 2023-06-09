package mg.disturb.Presence.DAO;

import jakarta.persistence.NoResultException;
import jakarta.persistence.criteria.*;
import mg.disturb.Presence.Model.*;
import mg.disturb.Presence.Model.EventM;
import mg.disturb.Presence.Model.EventMRepeated;
import mg.disturb.Presence.Utils.DateUtils;

import java.sql.Time;
import java.util.List;

public class EventDAO extends AbstractDAOWithModel<EventM,String> {
    public EventDAO(){
        this.modelClass = EventM.class;
    }

    public List<EventM> findTakenTimeIntervall(Time startTime, Time endTime){
        CriteriaBuilder cb = getCriteriaBuilder();
        CriteriaQuery<EventM> q = cb.createQuery(EventM.class);
        Root<EventM> root = q.from(EventM.class);
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

    public List<EventMRepeated> getAllEventRepeated(){
        CriteriaBuilder cb = getCriteriaBuilder();
        CriteriaQuery<EventMRepeated> q = cb.createQuery(EventMRepeated.class);
        Root<EventMRepeated> root = q.from(EventMRepeated.class);
        q.select(root);
        return getEntityManager().createQuery(q).getResultList();
    }

    public List<EventMSingleDay> getEventsSingleOfTheWeek(){
        CriteriaBuilder cb = getCriteriaBuilder();
        CriteriaQuery<EventMSingleDay> q = cb.createQuery(EventMSingleDay.class);
        Root<EventMSingleDay> root = q.from(EventMSingleDay.class);
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

    public List<EventM> getTwoNearestEvent(){
        CriteriaBuilder cb = getCriteriaBuilder();
        CriteriaQuery<EventM> q = cb.createQuery(EventM.class);
        Root<EventM> root = q.from(EventM.class);
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

package mg.disturb.Presence.DAO;

import jakarta.persistence.NoResultException;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;
import mg.disturb.Presence.Model.Event;
import mg.disturb.Presence.Model.Presence;
import mg.disturb.Presence.Utils.DateUtils;

import java.sql.Date;
import java.util.List;

public class PresenceDAO extends AbstractDAOWithModel<Presence,String> {
    public PresenceDAO(){
        this.modelClass = Presence.class;
    }

    public Presence getCurrentPresenceFrom(Event event){
        CriteriaBuilder cb = getCriteriaBuilder();
        CriteriaQuery<Presence> q = cb.createQuery(Presence.class);
        Root<Presence> root = q.from(Presence.class);
        Join<Presence,Event> evnts = root.join("event");
        q.select(root);
        q.where(
                cb.and(
                        cb.equal(
                                root.get("presenceDate"),
                                DateUtils.getCurrentDate()
                        ),
                        cb.equal(
                                evnts.get("eventId"),
                                event.getEventId()
                        )
                )
        );

        try {
            return getEntityManager().createQuery(q).setMaxResults(1).getSingleResult();
        } catch (NoResultException e){
            return null;
        }
    }

    public List<Presence> getHistoryFrom(Date fromDate){
        CriteriaBuilder cb = getCriteriaBuilder();
        CriteriaQuery<Presence> q = cb.createQuery(Presence.class);
        Root<Presence> root = q.from(Presence.class);
        Join<Presence,Event> eventJoin = root.join("event");
        q.select(root);
        q.where(
              cb.between(
                      root.get("presenceDate"),
                      fromDate,
                      DateUtils.getCurrentDate()
              )
        );

        q.orderBy(
                cb.desc(root.get("presenceDate")),
                cb.desc(eventJoin.get("beginTime"))
        );

        try {
            return getEntityManager().createQuery(q).getResultList();
        } catch (NoResultException e){
            return null;
        }
    }

}

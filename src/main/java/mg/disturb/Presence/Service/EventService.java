package mg.disturb.Presence.Service;

import mg.disturb.Presence.DAO.EventDAO;
import mg.disturb.Presence.Model.EventM;
import mg.disturb.Presence.Model.EventMRepeated;
import mg.disturb.Presence.Model.EventMSingleDay;
import mg.disturb.Presence.Utils.DateUtils;
import mg.disturb.Presence.Utils.EventServiceUtils;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;


public class EventService {
    private static final EventDAO eventDAO = new EventDAO();
    private static EventM nearestEvent;

    /* CREER UNE EVENEMENT UNIQUE */
    public static EventMSingleDay createForOnlyOneDay(
            String eventName,
            Time beginTime,
            Time endTime,
            Date eventDate
    ) {

        EventMSingleDay event = new EventMSingleDay();
        event.setEventDate(eventDate);
        event.setEndTime(endTime);
        event.setBeginTime(beginTime);
        event.setEventName(eventName);
        eventDAO.persiste(event);

        return event;
    }


    /* * CREER UNE EVENEMENT REPETE* */
    public static EventMRepeated createEventRepeated(
            String eventName,
            Time beginTime,
            Time endTime,
            int repeatedDayId
    ) {
        EventMRepeated event = new EventMRepeated();
        event.setEventName(eventName);
        event.setBeginTime(beginTime);
        event.setEndTime(endTime);
        eventDAO.persiste(event);
        event.setRepeatedDayId(repeatedDayId);
        eventDAO.persiste(event);
        return event;
    }

    /* METTRE A JOUR UNE EVENEMENT UNIQUE */
    public static void update(EventMSingleDay event) {
        eventDAO.update(event);
    }

    /* * METTRE A JOUR UNE EVENEMENT REPETE* */
    public static void update(EventMRepeated event) {
        eventDAO.update(event);
    }

    public static void update(EventM event){
        eventDAO.update(event);
    }

    public static List<EventM> getEventOfTheWeek(){
        List<EventMRepeated> eventsRepeated = eventDAO.getAllEventRepeated();
        List<EventMSingleDay> eventSingleDaysOfWeek = eventDAO.getEventsSingleOfTheWeek();
        List<EventM> eventOfTheWeek = new ArrayList<>();
        eventOfTheWeek.addAll(eventsRepeated);
        eventOfTheWeek.addAll(eventSingleDaysOfWeek);
        return eventOfTheWeek;
    }

    public static EventM getNearestEvent(){
        if(nearestEvent == null || EventServiceUtils.isDelayedEvent(nearestEvent)){
            nearestEvent = fetchNearestEvent();
        }
        return nearestEvent;
    }

    public static void clearNearestEvent(){
        nearestEvent = null;
    }

    private static EventM fetchNearestEvent(){
        List<EventM> nearestTwoEvent = eventDAO.getTwoNearestEvent();
        if(nearestTwoEvent.size() == 2){
            if (
                    nearestTwoEvent.get(1).getClass() == EventMSingleDay.class
                            && nearestTwoEvent.get(0).getClass() == EventMRepeated.class
            ){
                return nearestTwoEvent.get(1);
            } else {
                return nearestTwoEvent.get(0);
            }
        } else if(nearestTwoEvent.size() == 1) {
            return nearestTwoEvent.get(0);
        }


        return null;
    }

    public static EventM findById(String id){
        return eventDAO.findById(id);
    }
    public static void delete(EventM event){
        eventDAO.delete(event);
    }
    public static List<EventM> findAll() { return eventDAO.findAll(); }
}

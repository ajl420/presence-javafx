package mg.disturb.Presence.Service;

import mg.disturb.Presence.DAO.EventDAO;
import mg.disturb.Presence.Model.Event;
import mg.disturb.Presence.Model.EventRepeated;
import mg.disturb.Presence.Model.EventSingleDay;
import mg.disturb.Presence.Utils.DateUtils;
import mg.disturb.Presence.Utils.EventServiceUtils;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;


public class EventService {
    private static final EventDAO eventDAO = new EventDAO();
    private static Event nearestEvent;

    /* CREER UNE EVENEMENT UNIQUE */
    public static EventSingleDay createForOnlyOneDay(
            String eventName,
            Time beginTime,
            Time endTime,
            Date eventDate
    ) throws Exception {
        if(EventServiceUtils.isDateTaken(beginTime,endTime,eventDate)){
            throw new Exception("Date deja prise!!!");
        }

        EventSingleDay event = new EventSingleDay();
        event.setEventDate(eventDate);
        event.setEndTime(endTime);
        event.setBeginTime(beginTime);
        event.setEventName(eventName);
        eventDAO.persiste(event);

        return event;
    }


    /* * CREER UNE EVENEMENT REPETE* */
    public static EventRepeated createEventRepeated(
            String eventName,
            Time beginTime,
            Time endTime,
            int repeatedDayId
    ) throws Exception {

        if(EventServiceUtils.isDateTaken(beginTime,endTime,repeatedDayId)){
            throw new Exception("Date deja prise!!!");
        }

        EventRepeated event = new EventRepeated();
        event.setEventName(eventName);
        event.setBeginTime(beginTime);
        event.setEndTime(endTime);
        eventDAO.persiste(event);
        event.setRepeatedDayId(repeatedDayId);
        eventDAO.persiste(event);
        return event;
    }

    /* METTRE A JOUR UNE EVENEMENT UNIQUE */
    public static void update(EventSingleDay event) throws Exception {
        Time beginTime = event.getBeginTime();
        Time endTime = event.getEndTime();
        Date eventDate = (Date) event.getEventDate();
        String eventId = event.getEventId();
        if (EventServiceUtils.isDateTaken(beginTime,endTime,eventDate,eventId)){
            throw new Exception("Date deja prise!!!");
        }

        eventDAO.update(event);
    }

    /* * METTRE A JOUR UNE EVENEMENT REPETE* */
    public static void update(EventRepeated event) throws Exception {
        Time beginTime = event.getBeginTime();
        Time endTime = event.getEndTime();
        int repeatedDayId = event.getRepeatedDayId();
        String eventId = event.getEventId();

        if(EventServiceUtils.isDateTaken(beginTime,endTime,repeatedDayId,eventId)){
            throw new Exception("Date deja prise!!!");
        }

        eventDAO.update(event);
    }

    public static void update(Event event){
        eventDAO.update(event);
    }

    public static List<Event> getEventOfTheWeek(){
        List<EventRepeated> eventsRepeated = eventDAO.getAllEventRepeated();
        List<EventSingleDay> eventSingleDaysOfWeek = eventDAO.getEventsSingleOfTheWeek();
        List<Event> eventOfTheWeek = new ArrayList<>();
        eventOfTheWeek.addAll(eventsRepeated);
        eventOfTheWeek.addAll(eventSingleDaysOfWeek);
        return eventOfTheWeek;
    }

    public static Event getNearestEvent(){
        if(nearestEvent == null || EventServiceUtils.isDelayedEvent(nearestEvent)){
            nearestEvent = fetchNearestEvent();
        }
        return nearestEvent;
    }

    private static Event fetchNearestEvent(){
        List<Event> nearestTwoEvent = eventDAO.getTwoNearestEvent();
        if(nearestTwoEvent.size() == 2){
            if (
                    nearestTwoEvent.get(1).getClass() == EventSingleDay.class
                            && nearestTwoEvent.get(0).getClass() == EventRepeated.class
            ){
                return nearestTwoEvent.get(1);
            } else {
                return nearestTwoEvent.get(0);
            }
        } else if(nearestTwoEvent.size() == 1) {
            return nearestTwoEvent.get(0);
        }

        Event voidEvent = new Event();
        voidEvent.setEndTime(new Time(23,59,59));
        voidEvent.setEventName("VOID_EVENT");
        return voidEvent;
    }

    public static Event findById(String id){
        return eventDAO.findById(id);
    }

    public static void delete(Event event){
        eventDAO.delete(event);
    }
}

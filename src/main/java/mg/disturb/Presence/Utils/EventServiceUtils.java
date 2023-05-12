package mg.disturb.Presence.Utils;

import mg.disturb.Presence.DAO.EventDAO;
import mg.disturb.Presence.Model.Event;
import mg.disturb.Presence.Model.EventRepeated;
import mg.disturb.Presence.Model.EventSingleDay;
import mg.disturb.Presence.Model.Days;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

public class EventServiceUtils {

    private static EventDAO eventDAO = new EventDAO();
    public static boolean isDateTaken(
            Time beginTime,
            Time endTime,
            Date eventDate,
            String exceptionId
    ){
        List<Event> events = eventDAO.findTakenTimeIntervall( beginTime, endTime );
        for (Event takenEvent:events) {
            if(
                    takenEvent.getClass() == EventSingleDay.class
                            && !exceptionId.equals(takenEvent.getEventId())
            ){
                Date takenDate = (Date) ((EventSingleDay) takenEvent).getEventDate();
                if(takenDate.equals(eventDate)){
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isDateTaken(
            Time beginTime,
            Time endTime,
            int repeatedDayId,
            String exceptionId
    ){

        List<Event> events = eventDAO.findTakenTimeIntervall( beginTime, endTime );

        for (Event takenEvent:events) {
             if (
                    takenEvent.getClass() == EventRepeated.class
                            && !exceptionId.equals(takenEvent.getEventId())
             ) {
                EventRepeated takenEventRepeated = (EventRepeated) takenEvent;
                return takenEventRepeated.getRepeatedDayId() == repeatedDayId;
            }
        }

        return false;
    }

    public static boolean isDateTaken(
            Time beginTime,
            Time endTime,
            int repeatedDayId
    ){
        return isDateTaken(beginTime,endTime,repeatedDayId,"");
    }

    public static boolean isDateTaken(
            Time beginTime,
            Time endTime,
            Date eventDate
    ){
        return isDateTaken(beginTime,endTime,eventDate,"");
    }

    public static boolean isActiveEvent(Event event){
        Time currentTime = DateUtils.getCurrentTime();

        int diff = DateUtils.compareTime(
                currentTime,
                event.getBeginTime()
        );

        return diff >= 0;
    }

    public static boolean isDelayedEvent(Event event){
        Time currentTime = DateUtils.getCurrentTime();

        int diff = DateUtils.compareTime(
                currentTime,
                event.getEndTime()
        );

        return diff >= 0;
    }
}

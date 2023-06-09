package mg.disturb.Presence.Utils;

import mg.disturb.Presence.DAO.EventDAO;
import mg.disturb.Presence.Model.*;
import mg.disturb.Presence.Model.EventM;
import mg.disturb.Presence.Model.EventMRepeated;

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
        List<EventM> events = eventDAO.findTakenTimeIntervall( beginTime, endTime );
        for (EventM takenEvent:events) {
            if(
                    takenEvent.getClass() == EventMSingleDay.class
                            && !exceptionId.equals(takenEvent.getEventId())
            ){
                Date takenDate = (Date) ((EventMSingleDay) takenEvent).getEventDate();
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

        List<EventM> events = eventDAO.findTakenTimeIntervall( beginTime, endTime );

        for (EventM takenEvent:events) {
             if (
                    takenEvent.getClass() == EventMRepeated.class
                            && !exceptionId.equals(takenEvent.getEventId())
             ) {
                EventMRepeated takenEventRepeated = (EventMRepeated) takenEvent;
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

    public static boolean isActiveEvent(EventM event){
        Time currentTime = DateUtils.getCurrentTime();

        int diff = DateUtils.compareTime(
                currentTime,
                event.getBeginTime()
        );

        return diff >= 0;
    }

    public static boolean isDelayedEvent(EventM event){
        Time currentTime = DateUtils.getCurrentTime();

        int diff = DateUtils.compareTime(
                currentTime,
                event.getEndTime()
        );

        return diff >= 0;
    }
}

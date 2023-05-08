package com.example.demo.Service;

import com.example.demo.DAO.EventDAO;
import com.example.demo.DAO.WeekDAO;
import com.example.demo.Model.Event;
import com.example.demo.Model.EventRepeated;
import com.example.demo.Model.EventSingleDay;
import com.example.demo.Model.Week;
import com.example.demo.Utils.DateUtils;

import java.sql.Date;
import java.sql.Time;
import java.util.List;


public class EventService {
    private static final EventDAO eventDAO = new EventDAO();
    private static final WeekDAO weekDAO = new WeekDAO();


    /* CREER UNE EVENEMENT UNIQUE */
    public static EventSingleDay createForOnlyOneDay(
            String eventName,
            Time beginTime,
            Time endTime,
            Date eventDate
    ) throws Exception {

        List<Event> events = eventDAO.findTakenTimeIntervall( beginTime, endTime );

        for (Event takenEvent:events) {
            if(takenEvent.getClass() == EventSingleDay.class){
                Date takenDate = (Date) ((EventSingleDay) takenEvent).getEventDate();
                if(takenDate.equals(eventDate)){
                    throw new Exception("Date deja occupe");
                }
            } else if (takenEvent.getClass() == EventRepeated.class) {
                EventRepeated takenEventRepeated = (EventRepeated) takenEvent;
                List<Integer> daysId = takenEventRepeated
                        .getRepeated()
                        .stream()
                        .map(Week::getDayId)
                        .toList();
                int dateDayOfWeek = DateUtils.getDayOfWeek(eventDate);
                if(daysId.contains(dateDayOfWeek)){
                    throw new Exception("Date deja occupe");
                }
            }
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
            List<Week> repeatedDay
    ) throws Exception {
        List<Integer> repeatedDaysId = repeatedDay
                .stream()
                .map(Week::getDayId)
                .toList();

        List<Event> events = eventDAO.findTakenTimeIntervall( beginTime, endTime );

        for (Event takenEvent:events) {
            if(takenEvent.getClass() == EventSingleDay.class){
                Date takenDate = (Date) ((EventSingleDay) takenEvent).getEventDate();
                int dateDayOfWeek = DateUtils.getDayOfWeek(takenDate);
                if(repeatedDaysId.contains(dateDayOfWeek)){
                    throw new Exception("Date deja occupe");
                }
            } else if (takenEvent.getClass() == EventRepeated.class) {
                EventRepeated takenEventRepeated = (EventRepeated) takenEvent;
                List<Integer> takenDaysId = takenEventRepeated
                        .getRepeated()
                        .stream()
                        .map(Week::getDayId)
                        .toList();
                for (int dayId: repeatedDaysId) {
                    if(takenDaysId.contains(dayId)){
                        throw new Exception("Date deja occupe");
                    }
                }
            }
        }

        EventRepeated event = new EventRepeated();
        event.setEventName(eventName);
        event.setBeginTime(beginTime);
        event.setEndTime(endTime);
        eventDAO.persiste(event);
        event.getRepeated().addAll(repeatedDay);
        eventDAO.persiste(event);
        return event;
    }
}

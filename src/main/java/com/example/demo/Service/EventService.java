package com.example.demo.Service;

import com.example.demo.DAO.EventDAO;
import com.example.demo.Model.Event;

import java.sql.Date;
import java.sql.Time;

public class EventService {
    private static final EventDAO eventDAO = new EventDAO();

    public static Event createForOnlyOneDay(
            String eventName,
            Time beginTime,
            Time endTime,
            Date eventDate
    ){
        Event event = new Event();
        event.setEventDate(eventDate);
        event.setEndTime(endTime);
        event.setBeginTime(beginTime);
        event.setEventName(eventName);
        eventDAO.persiste(event);
        eventDAO.detach(event);
        return event;
    }
}

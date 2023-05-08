package com.example.demo.DAO;

import com.example.demo.Model.Event;

public class EventDAO extends AbstractDAOWithModel<Event,String> {
    public EventDAO(){
        this.modelClass = Event.class;
    }
}

package com.example.demo.DAO;

import com.example.demo.Model.Presence;

public class PresenceDAO extends AbstractDAOWithModel<Presence,Integer> {
    public PresenceDAO(){
        this.modelClass = Presence.class;
    }
}

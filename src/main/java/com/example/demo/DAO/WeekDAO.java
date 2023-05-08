package com.example.demo.DAO;


import com.example.demo.Model.Week;

public class WeekDAO extends AbstractDAOWithModel<Week,Integer> {
    public WeekDAO(){
        this.modelClass = Week.class;
    }
}

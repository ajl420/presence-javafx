package com.example.demo.Service;

import com.example.demo.DAO.WeekDAO;
import com.example.demo.Model.Week;

import java.util.List;

public class WeekService {
    private static WeekDAO weekDAO = new WeekDAO();

    public static List<Week> getAll(){
        return weekDAO.findAll();
    }

    public static void initData(){
        String[] daysLabel = {
                "Di","Lu","Ma","Me","Je","Ve","Sa"
        };

        String[] daysName = {
                "Dimanche","Lundi","Mardi",
                "Mercredi","Jeudi","Vendredi","Samedi"
        };

        for (int i = 0; i < daysLabel.length; i++) {
            String dayLabel = daysLabel[i];
            String dayName = daysName[i];
            Week day = weekDAO.findById(i+1);
            if(day == null){
                day = new Week();
                day.setDayId(i+1);
                day.setDayLabel(dayLabel);
                day.setDayName(dayName);
                weekDAO.persiste(day);
            }
        }
    }
}

package com.example.demo.Service;

import com.example.demo.DAO.PresenceDAO;
import com.example.demo.DAO.StudentDAO;
import com.example.demo.Model.Event;
import com.example.demo.Model.Presence;
import com.example.demo.Model.Student;

import java.util.List;

public class PresenceService {
    private static final PresenceDAO presenceDAO = new PresenceDAO();
    private static final StudentDAO studentDAO = new StudentDAO();


    public static void preparePresence(Event event){
        List<Student> students = studentDAO.findAll();
        for (Student student: students) {
            Presence presence = new Presence();
            presence.setPresent(false);
            presence.setStudent(student);
            presence.setEvent(event);
            presenceDAO.persiste(presence);
        }
    }
}

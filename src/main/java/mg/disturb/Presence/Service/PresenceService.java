package mg.disturb.Presence.Service;

import mg.disturb.Presence.DAO.PresenceDAO;
import mg.disturb.Presence.DAO.StudentDAO;
import mg.disturb.Presence.Model.*;
import mg.disturb.Presence.Utils.DateUtils;
import mg.disturb.Presence.Utils.EventServiceUtils;

import java.sql.Date;
import java.util.List;

public class PresenceService {
    private static final PresenceDAO presenceDAO = new PresenceDAO();
    private static Presence currentPresence;

    private static Presence preparePresence(Event event){
        Presence presence = new Presence();
        presence.setPresenceDate(DateUtils.getCurrentDate());
        presence.setEvent(event);
        persiste(presence);
        EventService.update(event);
        return presence;
    }

    public static Presence getCurrentPresence() {
        Event nearestEvent = EventService.getNearestEvent();
        if (
                (currentPresence == null ||
                EventServiceUtils.isDelayedEvent(currentPresence.getEvent())) &&
                !nearestEvent.getEventName().equals("VOID_EVENT")
        ){
            Presence fetchedCurrentPresence =
                    presenceDAO.getCurrentPresenceFrom(nearestEvent);

            if (
                    fetchedCurrentPresence == null &&
                    EventServiceUtils.isActiveEvent(nearestEvent)

            ){
                currentPresence = preparePresence(nearestEvent);
            } else {
                currentPresence = fetchedCurrentPresence;
            }
        }

        return currentPresence;
    }


    public static void setToPresent(Presence presence,Student student){
        presence.addStudent(student);
        StudentService.update(student);
        update(presence);
    }

    public static void setToAbsent(Presence presence,Student student){
        presence.removeStudent(student);
        StudentService.update(student);
        update(presence);
    }

    public static List<Student> absentStudentFrom(
            Presence presence,
            List<Student> students
    ){
        students.removeIf(student -> student.isPresentOn(presence));
        return students;
    }

    public static void update(Presence presence){
        presenceDAO.update(presence);
    }

    public static void persiste(Presence presence){
        presenceDAO.persiste(presence);
    }

    public static Presence findById(String presenceId){
        return presenceDAO.findById(presenceId);
    }

    public static List<Presence> getHistoryFrom(Date fromTo){
        return presenceDAO.getHistoryFrom(fromTo);
    }

    public static void abordAPresence(Presence presence){
        presenceDAO.delete(presence);
    }

    public static List<Student> presentStudentFrom(Presence presence){
        return (List<Student>) presence.getStudents();
    }


}

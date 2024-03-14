package mg.disturb.Presence.ServiceTest;

import mg.disturb.Presence.Model.Presence;
import mg.disturb.Presence.Model.Student;
import mg.disturb.Presence.Service.PresenceService;
import mg.disturb.Presence.Service.StudentService;

import java.sql.Date;
import java.util.List;

public class PresenceServiceTest {
//    public static void preparePresence(){
//        PresenceService.preparePresence();
//    }

    public static void setToPresente(){
        Student s1 = StudentService.findById("079I22");
//        Presence p = PresenceService.findById("4653e8d5-eff4-4135-b89a-2081868bcae5");
        Presence p = PresenceService.getCurrentPresence();
        PresenceService.setToPresent(p,s1);
    }

    public static void setToAbsent(){
        Student s1 = StudentService.findById("079I21");
//        Presence p = PresenceService.findById("4653e8d5-eff4-4135-b89a-2081868bcae5");
        Presence p = PresenceService.getCurrentPresence();
        PresenceService.setToAbsent(p,s1);
    }

    public static void getCurrentPresence(){
        Presence p = PresenceService.getCurrentPresence();
        System.out.println(p.toString());
    }

    public static void allPresentStudent(){
        Presence p = PresenceService.getCurrentPresence();
        for (Student student:PresenceService.presentStudentFrom(p)) {
            System.out.println(student);
        }
    }

    public static void allAbsentStudent(){
        Presence p = PresenceService.getCurrentPresence();
        List<Student> students = StudentService.search("L");
        for (Student student:PresenceService.absentStudentFrom(p,students)) {
            System.out.println(student);
        }
    }

    public static void getHistory(){
        for (Presence p: PresenceService.getHistoryFrom(new Date(123,4,2))) {
            System.out.println(p);
        }
    }

}

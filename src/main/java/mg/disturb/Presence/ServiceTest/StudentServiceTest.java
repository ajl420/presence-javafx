package mg.disturb.Presence.ServiceTest;

import mg.disturb.Presence.Model.Student;
import mg.disturb.Presence.Service.StudentService;

public class StudentServiceTest {
    public static void createStudent() throws Exception {
        Student s1 = StudentService.create(
                "079I23",
                "Lolo",
                "Lala"
        );

        Student s2 = StudentService.create(
                "079I24",
                "Jak's",
                "Toby"
        );

        System.out.println(s1.toString());
        System.out.println(s2.toString());
    }
}
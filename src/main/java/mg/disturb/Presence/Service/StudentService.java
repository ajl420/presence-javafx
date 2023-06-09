package mg.disturb.Presence.Service;

import mg.disturb.Presence.DAO.StudentDAO;
import mg.disturb.Presence.Model.Student;

import java.util.List;

public class StudentService {
    private static StudentDAO studentDAO = new StudentDAO();

    public static Student create(
            String numInscri,
            String nameStud,
            String fstNameStud
    ) {
        Student student = new Student();
        student.setNumInscri(numInscri);
        student.setNameStud(nameStud);
        student.setFstNameStud(fstNameStud);
        studentDAO.persiste(student);
        return student;
    }

    public static boolean isExist(String numInscri){
        Student student = studentDAO.findById(numInscri);
        return student != null;
    }
    public static void update(Student student){
        studentDAO.update(student);
    }

    public static Student findById(String numInscri){
        return studentDAO.findById(numInscri);
    }

    public static void delete(Student student){
        studentDAO.delete(student);
    }

    public static List<Student> findAll(){
        return studentDAO.findAll();
    }

    public static List<Student> search(String toSearch){
        return studentDAO.search("%"+toSearch+"%");
    }
}

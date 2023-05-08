package com.example.demo.Service;

import com.example.demo.DAO.StudentDAO;
import com.example.demo.Model.Student;

public class StudentService {
    private static StudentDAO studentDAO = new StudentDAO();

    public static Student create(
            String numInscri,
            String nameStud,
            String fstNameStud
    ) throws Exception {
        Student student = studentDAO.findById(numInscri);
        if(student != null){
            throw new Exception("Ce numero d'inscription a ete deja utiliser!");
        } else {
            student = new Student();
            student.setNumInscri(numInscri);
            student.setNameStud(nameStud);
            student.setFstNameStud(fstNameStud);
            studentDAO.persiste(student);
            return student;
        }
    }
}

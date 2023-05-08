package com.example.demo.DAO;

import com.example.demo.Model.Student;

import java.util.List;

public class MainDao {
    public static void main(String[] args) {

        Student s1 = new Student();
        s1.setNumInscri("079I22^&&**(9");
        s1.setFstNameStud("bltehfdhwed");
        s1.setNameStud("blhgtjdnerhghla");

        StudentDAO studentDAO = new StudentDAO();
//        studentDAO.persiste(s1);
//        Student s2 = studentDAO.findById("079I22");
//        List<Student> ss = studentDAO.searchByName("");
        List<Student> ss = studentDAO.findAll();

//        System.out.println(s2.getNameStud());
//        s2.setNameStud("AJL");
//        studentDAO.update(s2);
//        studentDAO.delete(s2);
        System.out.println(ss.size());

    }
}

